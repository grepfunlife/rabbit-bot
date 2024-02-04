val telegramVersion: String by project
val ktorVersion: String by project

plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21"
}

group = "red.rabbit"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation ("org.telegram:telegrambots:6.9.7.0")
    implementation("commons-codec:commons-codec"){
        version{
            strictly("1.16.0")
        }
    }
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC2")
    implementation("io.ktor:ktor-client-apache5:$ktorVersion")

    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("com.github.elbekD:kt-telegram-bot:2.2.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}