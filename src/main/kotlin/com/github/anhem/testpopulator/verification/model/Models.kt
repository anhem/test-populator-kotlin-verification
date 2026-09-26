package com.github.anhem.testpopulator.verification.model

import java.util.UUID

data class MyDataClass(
    val id: UUID,
    val name: String,
    val age: Int = 42,
    val tags: List<String> = emptyList()
)

sealed class MySealedClass {
    data class Success(val message: String) : MySealedClass()
    data class Error(val code: Int, val throwable: Throwable?) : MySealedClass()
}

@JvmInline
value class MyValueClass(val value: String)

data class MyKotlinClassWithJavaField(
    val javaPojo: MyJavaPojo,
    val stringValue: String
)

enum class MyEnum {
    ONE, TWO, THREE
}

object MySingleton {
    val name = "Singleton"
}

class MyClassWithDefaults(
    val required: String,
    val optional: String = "default",
    val anotherOptional: Int = 123
)

class MyClassWithCompanion(val value: String) {
    companion object {
        fun create(value: String) = MyClassWithCompanion(value)
    }
}

class MyClassWithNullables(val text: String?, val number: Int?)

class MyMutableClass(var mutableString: String, var mutableInt: Int)

class MyClassWithCollections(val strings: List<String>, val map: Map<String, Int>)

class MyClassWithSecondaryConstructor(val mainProp: String) {
    constructor(mainProp: String, extra: String) : this(mainProp)
}
