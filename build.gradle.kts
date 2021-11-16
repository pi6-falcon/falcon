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
version = "6.3.0"
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
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.0.4")
    // Cache
    implementation("com.github.ben-manes.caffeine:caffeine:3.0.4")
    // Utils + Kotlin
    implementation("io.github.microutils:kotlin-logging:2.0.11")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.21")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
    implementation("commons-validator:commons-validator:1.7")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth:3.0.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt")
    implementation("io.github.openfeign:feign-httpclient:11.5")
    // Storage
    implementation("com.github.derjust:spring-data-dynamodb:5.1.0")
    // Swagger
    implementation("org.springdoc:springdoc-openapi-ui:1.5.10")
    // Test
    testImplementation("io.kotest:kotest-assertions-core:4.6.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("uk.org.webcompere:system-stubs-core:1.2.0")
    testImplementation("uk.org.webcompere:system-stubs-jupiter:1.2.0")
    testImplementation("org.testcontainers:testcontainers:1.16.2")
    // Security
    implementation("io.jsonwebtoken:jjwt:0.9.1")
}

//
// val startDynamoDb = task<Exec>("startDynamoDB") {
//     commandLine("bash", "-c", "docker run -p 8000:8000 -d tray/dynamodb-local -inMemory -sharedDb -port 8000")
// }
//
//
// val createUserDynamoDBTable = task<Exec>("createUserDynamoDBTable") {
//     mustRunAfter(startDynamoDb)
//     commandLine("bash", "-c", "aws dynamodb create-table --endpoint-url http://localhost:8000 --table-name user --attribute-definitions AttributeName=username,AttributeType=S --key-schema AttributeName=username,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5")
// }
//
// val createUrlDynamoDBTable = task<Exec>("createUrlDynamoDBTable") {
//     mustRunAfter(startDynamoDb)
//     commandLine("bash", "-c", "aws dynamodb create-table --endpoint-url http://localhost:8000 --table-name url --attribute-definitions AttributeName=short_url,AttributeType=S --key-schema AttributeName=short_url,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5")
// }
//
// val createRedirectHistoryDynamoDBTable = task<Exec>("createRedirectHistoryDynamoDBTable") {
//     mustRunAfter(startDynamoDb)
//     commandLine("bash", "-c", "aws dynamodb create-table --endpoint-url http://localhost:8000 --table-name redirect_history --attribute-definitions AttributeName=id,AttributeType=S --key-schema AttributeName=id,KeyType=HASH --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1")
// }
//
// val stopDynamoDB = task<Exec>("stopDynamoDB") {
//     commandLine("bash", "-c", "id=\$(docker ps | grep \"tray/dynamodb-local\" | awk '{print \$1}');if [[ \${id} ]]; then docker rm \$id --force; fi")
// }

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
    // dependsOn(startDynamoDb)
    // dependsOn(createUserDynamoDBTable)
    // dependsOn(createUrlDynamoDBTable)
    // dependsOn(createRedirectHistoryDynamoDBTable)
    finalizedBy(tasks.jacocoTestReport)
    // finalizedBy(stopDynamoDB)
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
                exclude(
                    // Root (main)
                    "**/falcon/*.*",
                    // Utils
                    "**/MutableHttpServletRequest.*",
                    // Log class
                    "**/*log*.class",
                    "**/*Entity*.class",
                    "**/configuration/**"
                )
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
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
    }
}
