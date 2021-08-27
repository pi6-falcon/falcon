import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.plugin.noarg")
}

tasks {
    withType(Jar::class) {
        enabled = true
    }

    withType(BootJar::class) {
        enabled = false
    }
}

