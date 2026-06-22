package com.github.anhem.testpopulator.verification

import com.github.anhem.testpopulator.PopulateFactory
import com.github.anhem.testpopulator.config.PopulateConfig
import com.github.anhem.testpopulator.verification.model.*
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
        com.github.anhem.testpopulator.verification.testutil.GeneratedCodeUtil.assertGeneratedCode(result, config)
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
        com.github.anhem.testpopulator.verification.testutil.GeneratedCodeUtil.assertGeneratedCode(result, staticConfig)
    }

    @Test
    fun `can populate singleton`() {
        val result = factory.populate(MySingleton::class.java)
        assertThat(result).isNotNull
        assertThat(result).isSameAs(MySingleton)
        assertThat(result.name).isEqualTo("Singleton")
        com.github.anhem.testpopulator.verification.testutil.GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate class with defaults`() {
        val result = factory.populate(MyClassWithDefaults::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.required).isNotBlank()
        assertThat(result.optional).isNotEqualTo("default")
        assertThat(result.anotherOptional).isNotEqualTo(123)
        com.github.anhem.testpopulator.verification.testutil.GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate enum`() {
        val result = factory.populate(MyEnum::class.java)
        assertThat(result).isNotNull
        assertThat(result).isIn(*MyEnum.entries.toTypedArray())
        com.github.anhem.testpopulator.verification.testutil.GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate sealed class success subclass`() {
        val result = factory.populate(MySealedClass.Success::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.message).isNotBlank()
        com.github.anhem.testpopulator.verification.testutil.GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate sealed class error subclass`() {
        val result = factory.populate(MySealedClass.Error::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.code).isNotZero()
        assertThat(result.throwable).isNotNull()
        com.github.anhem.testpopulator.verification.testutil.GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate value class`() {
        val result = factory.populate(MyValueClass::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.value).isNotBlank()
        com.github.anhem.testpopulator.verification.testutil.GeneratedCodeUtil.assertGeneratedCode(result, config)
    }

    @Test
    fun `can populate kotlin class with java field`() {
        val result = factory.populate(MyKotlinClassWithJavaField::class.java)
        assertThat(result).isNotNull
        assertThat(result).hasNoNullFieldsOrProperties()
        assertThat(result.javaPojo).isNotNull
        assertThat(result.javaPojo.stringValue).isNotBlank()
        com.github.anhem.testpopulator.verification.testutil.GeneratedCodeUtil.assertGeneratedCode(result, config)
    }
}
