package io.viewpoint.moviedatabase.buildlogic

import io.viewpoint.moviedatabase.buildlogic.extensions.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
                apply("org.jetbrains.kotlinx.kover")
            }
            configureKotlinJvm()
            dependencies {
                add("testImplementation", kotlin("test"))
            }
        }
    }
}
