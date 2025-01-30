plugins {
    kotlin("jvm") version "1.9.20"
    id("antlr")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    antlr("org.antlr:antlr4:4.13.1")
    implementation("org.antlr:antlr4-runtime:4.13.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

// ANTLR configuration
tasks.withType<AntlrTask> {
    maxHeapSize = "64m"
    arguments = listOf(
        "-lib", "${layout.buildDirectory.dir("generated-src/antlr/main").get().asFile.absolutePath}",
        "-visitor",
        "-long-messages"
    )
    outputDirectory = layout.buildDirectory.dir("generated-src/antlr/main").get().asFile
}

// Unified task dependencies
tasks.compileJava {
    dependsOn(tasks.generateGrammarSource)
    mustRunAfter(tasks.generateGrammarSource)
    source(layout.buildDirectory.dir("generated-src/antlr/main"))
}

tasks.compileKotlin {
    dependsOn(tasks.generateGrammarSource)
    mustRunAfter(tasks.generateGrammarSource)
}

tasks.compileTestJava {
    dependsOn(tasks.generateTestGrammarSource)
    mustRunAfter(tasks.generateTestGrammarSource)
}

tasks.generateTestGrammarSource {
    dependsOn(tasks.generateGrammarSource)
    mustRunAfter(tasks.generateGrammarSource)
}

sourceSets {
    main {
        java {
            srcDir(layout.buildDirectory.dir("generated-src/antlr/main"))
        }
    }
}