val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val kotestVersion: String by project
val valiktorVersion: String by project
val bcryptVersion: String by project
val exposedVersion: String by project
val postgresqlVersion: String by project
val dotenvVersion: String by project
val loggingVersion: String by project
val kotlinFakerVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
}

tasks.withType<Test> {
   useJUnitPlatform()
}

group = "com.kastro"
version = "0.0.1"
application {
    mainClass.set("com.kastro.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.valiktor:valiktor-core:$valiktorVersion")
    implementation("at.favre.lib:bcrypt:$bcryptVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("io.github.cdimascio:dotenv-kotlin:$dotenvVersion")
    implementation("io.github.microutils:kotlin-logging:$loggingVersion")
    implementation("io.github.serpro69:kotlin-faker:$kotlinFakerVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}