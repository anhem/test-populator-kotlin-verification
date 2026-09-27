package com.github.anhem.testpopulator.verification

import com.github.anhem.testpopulator.PopulateFactory
import com.github.anhem.testpopulator.config.PopulateConfig
import com.github.anhem.testpopulator.verification.model.*
import com.github.anhem.testpopulator.verification.testutil.GeneratedCodeUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PopulationTest {

    private val config = PopulateConfig.builder()
        .kotlinSupport(true)
        .and()
        .objectFactory(true)
        .build()
    private val factory = PopulateFactory(config)

    @Test
    fun `can populate data class`() {
        val result = factory.populate(MyDataClass::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.id).isNotNull()
        assertThat(result.name).isNotBlank()
        assertThat(result.tags).isNotEmpty()
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate data class with default values`() {
        val defaultConfig = PopulateConfig.builder()
            .kotlinSupport(true)
            .defaultValues(true)
            .and()
            .objectFactory(true)
            .build()
        val defaultFactory = PopulateFactory(defaultConfig)

        val result = defaultFactory.populate(MyDataClass::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.age).isEqualTo(42)
        assertThat(result.tags).isEmpty()
    }

    @Test
    fun `can populate class with companion factory`() {
        val staticConfig = PopulateConfig.builder()
            .kotlinSupport(true)
            .and()
            .staticMethodStrategy()
            .and()
            .objectFactory(true)
            .build()
        val staticFactory = PopulateFactory(staticConfig)

        val result = staticFactory.populate(MyClassWithCompanion::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.value).isNotBlank()
        GeneratedCodeUtil.assertGeneratedCode(result, staticConfig)
    }

    @Test
    fun `can populate singleton`() {
        val result = factory.populate(MySingleton::class.java)
        assertThat(result).isNotNull
        assertThat(result).isSameAs(MySingleton)
        assertThat(result.name).isEqualTo("Singleton")
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate class with defaults`() {
        val result = factory.populate(MyClassWithDefaults::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.required).isNotBlank()
        assertThat(result.optional).isNotEqualTo("default")
        assertThat(result.anotherOptional).isNotEqualTo(123)
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate enum`() {
        val result = factory.populate(MyEnum::class.java)
        assertThat(result).isNotNull
        assertThat(result).isIn(*MyEnum.entries.toTypedArray())
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate sealed class success subclass`() {
        val result = factory.populate(MySealedClass.Success::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.message).isNotBlank()
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate sealed class error subclass`() {
        val result = factory.populate(MySealedClass.Error::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.code).isNotZero()
        assertThat(result.throwable).isNotNull()
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate value class`() {
        val result = factory.populate(MyValueClass::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.value).isNotBlank()
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate kotlin class with java field`() {
        val result = factory.populate(MyKotlinClassWithJavaField::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.javaPojo).isNotNull
        assertThat(result.javaPojo.stringValue).isNotBlank()
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate class with nullables`() {
        val result = factory.populate(MyClassWithNullables::class.java)
        assertThat(result).isNotNull
        assertThat(result.text).isNotNull()
        assertThat(result.number).isNotNull()
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate mutable class`() {
        val result = factory.populate(MyMutableClass::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.mutableString).isNotBlank()
        assertThat(result.mutableInt).isNotZero()
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate class with collections`() {
        val result = factory.populate(MyClassWithCollections::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.strings).isNotEmpty()
        assertThat(result.map).isNotEmpty()
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate class with secondary constructor`() {
        val result = factory.populate(MyClassWithSecondaryConstructor::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.mainProp).isNotBlank()
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate java record`() {
        val result = factory.populate(MyJavaRecord::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.name()).isNotBlank()
        assertThat(result.value()).isNotZero()
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate java enum`() {
        val result = factory.populate(MyJavaEnum::class.java)
        assertThat(result).isNotNull
        assertThat(result).isIn(*MyJavaEnum.values())
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate java pojo with kotlin field`() {
        val result = factory.populate(MyJavaPojoWithKotlinField::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.myDataClass).isNotNull()
        assertThat(result.myJavaEnum).isNotNull()
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate class with vararg`() {
        val result = factory.populate(MyClassWithVararg::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.strings).isNotEmpty()
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate class with lateinit and lazy`() {
        val result = factory.populate(MyClassWithLateinitAndLazy::class.java)
        assertThat(result).isNotNull
        assertThat(result.lateinitString).isNotBlank()
        assertThat(result.lazyString).isEqualTo("lazy_default")
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate nested class`() {
        val result = factory.populate(MyOuterClass.MyNestedClass::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.nestedString).isNotBlank()
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate inner class`() {
        val result = factory.populate(MyOuterClass.MyInnerClass::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.innerString).isNotBlank()
        GeneratedCodeUtil.assertGeneratedCode(result, config)
    }
}
