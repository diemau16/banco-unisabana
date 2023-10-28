plugins {
    java
    id("org.springframework.boot") version "2.7.15"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.springdoc.openapi-gradle-plugin") version "1.7.0"
    jacoco
    id ("info.solidsoft.pitest") version "1.15.0"
    id("org.sonarqube") version "4.4.1.3373"
}

group = "com.banco"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:8.0.30")
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    testCompileOnly("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")

    testImplementation("com.h2database:h2:2.2.220")

    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")
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
        csv.required.set(true)
        xml.required.set(true)
    }
    classDirectories.setFrom(
            files(classDirectories.files.map {
                fileTree(it) {
                    exclude("**/com/banco/sucursal/persistencia/**", "**/com/banco/sucursal/controller/dto/**")
                }
            })
    )
}

jacoco {
    toolVersion = "0.8.8"
}

pitest{
    junit5PluginVersion.set("1.2.1")

}
tasks.sonarqube {
    dependsOn(tasks.jacocoTestReport)
}

sonarqube {
    properties {
        property("sonar.projectName", "Banco Unisabana")
        property("sonar.java.coveragePlugin", "jacoco")
        property("sonar.junit.reportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
    }
}
