plugins {
    kotlin("jvm") version "2.2.21"
}

group = "com.github.anhem"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.assertj:assertj-core:3.27.7")
    testImplementation("com.github.anhem:test-populator:1.1.1-SNAPSHOT")
    testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:2.2.21")
}

tasks.test {
    useJUnitPlatform()
    jvmArgumentProviders.add(CommandLineArgumentProvider {
        listOf("-Dtest.classpath=${sourceSets.test.get().runtimeClasspath.asPath}")
    })
}

kotlin {
    jvmToolchain(21)
}
