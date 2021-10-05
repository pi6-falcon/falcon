import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.sonarqube") version "3.3"
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
    jacoco
}

jacoco {
    toolVersion = "0.8.7"
}

group = "com.falcon"
version = "2.0-dev-test"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    // Starter Dependencies
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // Cache
    implementation("com.github.ben-manes.caffeine:caffeine:3.0.3")
    // Utils + Kotlin
    implementation("io.github.microutils:kotlin-logging:2.0.11")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.21")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.5")
    implementation("commons-validator:commons-validator:1.7")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth:3.0.3")
    // Storage
    implementation("com.github.derjust:spring-data-dynamodb:5.1.0")
    // Swagger
    implementation("org.springdoc:springdoc-openapi-ui:1.5.10")
    // Test
    testImplementation("io.kotest:kotest-assertions-core:4.6.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("uk.org.webcompere:system-stubs-core:1.2.0")
    testImplementation("uk.org.webcompere:system-stubs-jupiter:1.2.0")
    // Security
    implementation("io.jsonwebtoken:jjwt:0.9.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        csv.required.set(true)
    }
}

tasks.withType<JacocoReport> {
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it).apply {
                // Main class
                exclude("**/falcon/*.*")
                // Configuration classes
//                exclude("**/falcon/configuration/**")
                // Exception classes
//                exclude("**/falcon/common/exception/resolver/**.*")
                // Log class (Kotlin compatibility)
                exclude("**/*log*.class")
            }
        }))
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.95".toBigDecimal()
            }
        }
    }
}
// Removes the **-plain.jar file on the build/libs
tasks.getByName<Jar>("jar") {
    enabled = false
}

sonarqube {
    properties {
        property("sonar.projectKey", "pi6-falcon_falcon")
        property("sonar.organization", "pi6-falcon")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
