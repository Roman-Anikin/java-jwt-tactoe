version = "1.0"

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    war
}

sourceSets {
    main {
        java {
            srcDirs(
                "src"
            )
        }
    }
}

buildscript {
    repositories {
        gradlePluginPortal()
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    manifest {
        attributes["Main-Class"] = "Application"
    }
    mergeServiceFiles()
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("org.springframework:spring-webmvc:5.3.39")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:9.0.84")

    implementation("com.fasterxml.jackson.core:jackson-core:2.17.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2")

    implementation("org.hibernate.orm:hibernate-core:6.5.2.Final")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("org.springframework.data:spring-data-jpa:3.4.1")
    implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.9.0")

    implementation("org.apache.logging.log4j:log4j-core:2.24.3")
    implementation("org.apache.logging.log4j:log4j-api:2.24.3")

    implementation("org.springframework.security:spring-security-web:5.8.16")
    implementation("org.springframework.security:spring-security-config:5.8.16")

    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

}
