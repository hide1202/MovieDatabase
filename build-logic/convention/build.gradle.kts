import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "io.viewpoint.moviedatabase.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradle)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradle)
    compileOnly(libs.kotlin.gradle)
    compileOnly(libs.room.gradle)
    compileOnly(libs.kotlin.kover)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "moviedatabase.android.application.compose"
            implementationClass =
                "io.viewpoint.moviedatabase.buildlogic.AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "moviedatabase.android.application"
            implementationClass =
                "io.viewpoint.moviedatabase.buildlogic.AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "moviedatabase.android.library.compose"
            implementationClass =
                "io.viewpoint.moviedatabase.buildlogic.AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "moviedatabase.android.library"
            implementationClass =
                "io.viewpoint.moviedatabase.buildlogic.AndroidLibraryConventionPlugin"
        }
        register("jvmLibrary") {
            id = "moviedatabase.jvm.library"
            implementationClass = "io.viewpoint.moviedatabase.buildlogic.JvmLibraryConventionPlugin"
        }
    }
}