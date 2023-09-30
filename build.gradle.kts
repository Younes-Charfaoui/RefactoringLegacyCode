import info.solidsoft.gradle.pitest.PitestPluginExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.8.10"
    id("info.solidsoft.pitest") version "1.9.0"
}

group = "com.charfaouiyounes"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}

configure<PitestPluginExtension> {
    mutationThreshold.set(71)
    coverageThreshold.set(70)
    threads.set(Runtime.getRuntime().availableProcessors())
    junit5PluginVersion.set("1.0.0")
    mutators.set(setOf("STRONGER"))
    targetClasses.set(setOf("com.charfaouiyounes.*"))
    targetTests.set(setOf("*"))
    outputFormats.set(setOf("HTML"))
    avoidCallsTo.set(setOf("kotlin.jvm.internal"))
}