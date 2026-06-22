package com.github.anhem.testpopulator.verification.testutil

import com.github.anhem.testpopulator.config.PopulateConfig
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.cli.common.ExitCode
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import java.io.File
import java.net.URLClassLoader
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.util.*
import java.util.concurrent.Future
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import java.util.stream.DoubleStream
import java.util.stream.IntStream
import java.util.stream.LongStream
import java.util.stream.Stream
import kotlin.io.path.absolutePathString

object GeneratedCodeUtil {

    private const val KOTLIN = ".kt"
    private const val CLASS = ".class"

    fun <T : Any> assertGeneratedCode(obj: T, populateConfig: PopulateConfig) {
        val clazz = obj::class.java
        val packageName = getPackageName(clazz)
        val className = formatClassName(clazz)
        val path = getPath(packageName, className, populateConfig)
        assertGeneratedCode(obj, path, packageName, clazz.simpleName, populateConfig)
    }

    private fun <T : Any> assertGeneratedCode(obj: T, path: Path, packageName: String, simpleName: String, populateConfig: PopulateConfig) {
        try {
            compileGeneratedFile(path, populateConfig)
            val clazz = loadClass<T>(path, packageName, path.fileName.toString().replace(KOTLIN, ""), populateConfig)
            var value = getStaticObjectFromClass(clazz, simpleName)
            var expectedObj: Any = obj
            if (obj.javaClass.annotations.any { it.annotationClass.simpleName == "JvmInline" }) {
                val unboxMethod = obj.javaClass.getDeclaredMethod("unbox-impl")
                expectedObj = unboxMethod.invoke(obj)
            }
            assertThat(value).usingRecursiveComparison()
                .withEqualsForType({ a, b -> a.toString().contentEquals(b) }, java.lang.StringBuilder::class.java)
                .withEqualsForType({ a, b -> a.toString().contentEquals(b) }, java.lang.StringBuffer::class.java)
                .withEqualsForType({ a, b -> (a as Throwable).message == (b as Throwable).message && a.javaClass == b.javaClass }, Throwable::class.java)
                .withEqualsForType({ a, b -> a.get() == b.get() }, AtomicInteger::class.java)
                .withEqualsForType({ a, b -> a.get() == b.get() }, AtomicLong::class.java)
                .withEqualsForType({ a, b -> a.get() == b.get() }, AtomicBoolean::class.java)
                .withEqualsForType({ _, _ -> true }, Stream::class.java)
                .withEqualsForType({ _, _ -> true }, IntStream::class.java)
                .withEqualsForType({ _, _ -> true }, LongStream::class.java)
                .withEqualsForType({ _, _ -> true }, DoubleStream::class.java)
                .withEqualsForType({ _, _ -> true }, Future::class.java)
                .withEqualsForType({ _, _ -> true }, Scanner::class.java)
                .withEqualsForType({ _, _ -> true }, Iterator::class.java)
                .isEqualTo(expectedObj)
        } finally {
            //removeGeneratedFiles(path)
        }
    }

    private fun compileGeneratedFile(path: Path, populateConfig: PopulateConfig) {
        val file = path.toFile()
        val cp = System.getProperty("test.classpath") ?: System.getProperty("java.class.path")
        val outDir = populateConfig.objectFactoryPath
        val args = arrayOf("-cp", cp, "-d", outDir, file.absolutePath)
        val compiler = K2JVMCompiler()
        val exitCode = compiler.exec(System.err, *args)
        assertThat(exitCode).`as`("compilation failed").isEqualTo(ExitCode.OK)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> loadClass(path: Path, packageName: String, className: String, populateConfig: PopulateConfig): Class<T> {
        assertThat(path.toFile()).exists()
        val classLoader = URLClassLoader(arrayOf(File(populateConfig.objectFactoryPath).toURI().toURL()), GeneratedCodeUtil::class.java.classLoader)
        return Class.forName(String.format("%s.%s", packageName, className), true, classLoader) as Class<T>
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> getStaticObjectFromClass(clazz: Class<T>, simpleName: String): T {
        val snakeCaseName = simpleName.replace("([a-z])([A-Z]+)".toRegex(), "$1_$2").uppercase()
        val variableName = "${snakeCaseName}_0"
        
        try {
            val getter = clazz.methods.find { it.name.equals("get$variableName", ignoreCase = true) }
            if (getter != null) {
                return getter.invoke(null) as T
            }
        } catch (e: Exception) {
            // ignore
        }

        return try {
            val field = clazz.getDeclaredField(variableName)
            field.isAccessible = true
            field.get(null) as T
        } catch (e: Exception) {
            val instance = clazz.getField("INSTANCE").get(null)
            val getter = clazz.methods.find { it.name.equals("get$variableName", ignoreCase = true) }
            if (getter != null) {
                getter.invoke(instance) as T
            } else {
                val field = clazz.getDeclaredField(variableName)
                field.isAccessible = true
                field.get(instance) as T
            }
        }
    }

    private fun removeGeneratedFiles(path: Path) {
        val classFile = path.resolveSibling(path.fileName.toString().replace(KOTLIN, CLASS))
        Files.deleteIfExists(path)
        Files.deleteIfExists(classFile)
    }

    private fun getPackageName(clazz: Class<*>): String {
        return clazz.name.substring(0, clazz.name.lastIndexOf("."))
    }

    private fun formatClassName(clazz: Class<*>): String {
        return clazz.simpleName + "_TestData"
    }

    private fun getPath(packageName: String, className: String, populateConfig: PopulateConfig): Path {
        val packagePath = packageName.replace(".", "/")
        return Path.of(populateConfig.objectFactoryPath, packagePath, "${className}_${encode(populateConfig)}.kt")
    }

    private fun encode(populateConfig: PopulateConfig): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val bytes = messageDigest.digest(populateConfig.toString().toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }.substring(0, 16)
    }
}
