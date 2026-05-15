package com.github.anhem.testpopulator.verification

import com.github.anhem.testpopulator.PopulateFactory
import com.github.anhem.testpopulator.config.PopulateConfig
import com.github.anhem.testpopulator.verification.model.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import com.github.anhem.testpopulator.exception.PopulateException
import org.assertj.core.api.Assertions.assertThatThrownBy

/**
 * These tests verify the behavior of test-populator when .kotlinSupport() is NOT enabled.
 * They serve as an "acceptance case" for why Kotlin support is necessary.
 */
class KotlinDisabledTest {

    private val config = PopulateConfig.builder().build()
    private val factory = PopulateFactory(config)

    @Test
    fun `populating data class without kotlin support fails (Expected Failure Case)`() {
        // ACCEPTANCE CASE: Without Kotlin support, test-populator fails to populate a data class
        // likely because it doesn't correctly identify or invoke the primary constructor.
        assertThatThrownBy {
            factory.populate(MyDataClass::class.java)
        }.isInstanceOf(PopulateException::class.java)
    }

    @Test
    fun `populating singleton without kotlin support fails (Expected Failure Case)`() {
        // ACCEPTANCE CASE: Without Kotlin support, test-populator fails to handle a Kotlin 'object' (singleton).
        assertThatThrownBy {
            factory.populate(MySingleton::class.java)
        }.isInstanceOf(PopulateException::class.java)
    }

    @Test
    fun `populating class with default parameters without kotlin support fails (Expected Failure Case)`() {
        // ACCEPTANCE CASE: Without Kotlin support, even a standard class with default parameters fails
        // if it lacks a no-args constructor and Kotlin metadata is ignored.
        assertThatThrownBy {
            factory.populate(MyClassWithDefaults::class.java)
        }.isInstanceOf(PopulateException::class.java)
    }
}
