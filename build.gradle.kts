val telegramVersion: String by project

plugins {
    kotlin("jvm") version "1.9.0"
}

group = "red.rabbit"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation ("org.telegram:telegrambots:6.9.7.0")
    implementation("commons-codec:commons-codec"){
        version{
            strictly("1.16.0")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}