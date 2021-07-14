import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
    application
}

group = "com.github.luigi2077"
version = "1.0-SNAPSHOT"
repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-compiler")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    // gson
    implementation("com.google.code.gson:gson:2.8.7")
    implementation("com.google.protobuf:protobuf-java:3.17.3")

    testImplementation(kotlin("test"))
    testImplementation("org.assertj:assertj-core:3.20.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Jar>() {
    manifest {
        attributes("Main-Class" to "org.javacs.MainKt")
    }
    from(configurations.compileClasspath.get().map { if (it.isDirectory()) it else zipTree(it) })
}
