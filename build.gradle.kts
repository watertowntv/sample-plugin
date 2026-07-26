import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    java
    kotlin("jvm") version "2.4.10"

    id("com.gradleup.shadow") version "9.6.1"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.21"
}

group = providers.gradleProperty("group").get()

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()

    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    compileOnly("org.junit.jupiter:junit-jupiter:6.1.2")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")

    compileOnly("io.papermc.paper:paper-api:26.2.build.+")
    compileOnly("5zaqws:zycos:+")

    paperweight.paperDevBundle("26.2.build.+")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    javadoc {
        options.encoding = "UTF-8"
    }
    compileKotlin {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_25)
    }
    processResources {
        filteringCharset = "UTF-8"
    }
    shadowJar {
        archiveClassifier.set("dist")
    }
    build {
        dependsOn(shadowJar)
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.add("-XXLanguage:+UnnamedLocalVariables")
        }
    }
}

paperweight {
    javaLauncher = javaToolchains.launcherFor {
        languageVersion = JavaLanguageVersion.of(25)
    }
}
