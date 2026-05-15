# Test Populator Kotlin Verification

This project serves as a comprehensive verification suite to ensure that [test-populator](https://github.com/anhem/test-populator) works seamlessly with Kotlin. It targets 100% verification of Kotlin-specific features and constructs.

## Purpose

The primary goal is to validate that `test-populator` can handle Kotlin's unique language features when `kotlinSupport()` is enabled in the configuration.

## Verified Kotlin Features

The following Kotlin constructs are currently verified in `src/main/kotlin/.../model/Models.kt`:

- **Data Classes**: Standard data classes with multiple fields.
- **Default Values**: Verification that `useDefaultValues()` configuration works as expected for Kotlin parameters.
- **Sealed Classes**: Population of specific sealed class implementations.
- **Value Classes** (JvmInline): Population of value classes.
- **Enums**: Standard Kotlin enums.
- **Singletons (Objects)**: Verification that `object` instances are correctly handled (returned as same instance).
- **Classes with Default Parameters**: Population of classes where some parameters have default values.
- **Companion Objects**: Verification of factory methods within companion objects using `staticMethodStrategy()`.

## Negative Testing (Acceptance Cases)

The project also includes `KotlinDisabledTest.kt`, which verifies that `test-populator` **fails** (throwing `PopulateException`) when `.kotlinSupport()` is not enabled for the following:
- Data Classes
- Singletons (Objects)
- Classes with default parameters (without no-args constructors)

This confirms that the Kotlin support flag is essential for handling Kotlin-specific metadata and constructs correctly.

## Project Structure

- `src/main/kotlin`: Contains the Kotlin models used for verification.
- `src/test/kotlin`: Contains the `PopulationTest` which executes the verification logic.
- `build.gradle.kts`: Project configuration including dependencies on `test-populator` and `assertj`.

## Running the Verification

To run the verification tests, use the Gradle wrapper:

```bash
./gradlew test
```

## Configuration Example

To enable Kotlin support in your own project:

```kotlin
val config = PopulateConfig.builder()
    .kotlinSupport()
    .build()
val factory = PopulateFactory(config)
val myData = factory.populate(MyDataClass::class.java)
```
