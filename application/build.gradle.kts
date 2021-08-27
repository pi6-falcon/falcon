plugins {
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.plugin.noarg")
}

dependencies {
    implementation(project(":domain"))
    //     Spring Framework
//    implementation("org.springframework.boot:spring-boot-starter-actuator")
//    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    //    Swagger
//    implementation("io.springfox:springfox-boot-starter:3.0.0")
//    implementation("io.springfox:springfox-spring-webflux:3.0.0")
    //     Starter Routing
//    implementation("com.mercadolibre:spring-boot-starter-routing:2.1.0")
//    implementation("com.mercadolibre:routing:2.2.1")
    //     Test
//    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:Hoxton.SR5")
    }
}
