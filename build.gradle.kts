plugins {
    id("java")
    id("application")    
    id("io.spring.dependency-management") version "1.1.0"
    id("org.springframework.boot") version "3.3.2"
}

group = "com.metao.transaction"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    testImplementation("com.h2database:h2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))

    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events ("PASSED", "SKIPPED", "FAILED")
    }
}