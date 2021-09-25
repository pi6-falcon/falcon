import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
    jacoco
    id("org.sonarqube") version "3.3"
}

group = "com.falcon"
version = "1.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    // Starter Dependencies
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.5.3")
    implementation("org.springframework.boot:spring-boot-starter-web:2.5.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.4")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.21")
    implementation("org.springframework.boot:spring-boot-starter-cache:2.5.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.3")
    developmentOnly("org.springframework.boot:spring-boot-devtools:2.5.3")
    // Test
    testImplementation("io.kotest:kotest-assertions-core:4.6.0")
    implementation("org.junit.jupiter:junit-jupiter:5.7.2")
    implementation("io.mockk:mockk:1.12.0")
    runtimeOnly("com.h2database:h2")
    // Cache
    implementation("com.github.ben-manes.caffeine:caffeine:3.0.3")
    // Utils
    implementation("io.github.microutils:kotlin-logging:2.0.10")
    implementation("org.springframework.cloud:spring-cloud-sleuth:3.0.3")
    // Storage
    implementation("com.amazonaws:aws-java-sdk-dynamodb:1.11.64")
    implementation("com.github.derjust:spring-data-dynamodb:5.1.0")
    // Swagger
    implementation("org.springdoc:springdoc-openapi-ui:1.5.10")
    // Security
    implementation("org.springframework.boot:spring-boot-starter-security:2.5.4")
    implementation("io.jsonwebtoken:jjwt:0.6.0")
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
    classDirectories.setFrom(
        sourceSets.main.get().output.asFileTree.matching {
            exclude("com/falcon/falcon/falcon/*.*")
            exclude("**/*log*.class")
        }
    )
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

sonarqube {
    properties {
        property("sonar.sourceEncoding", "UTF-8")
    }
}
