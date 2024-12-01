package io.viewpoint.moviedatabase.buildlogic.extensions

import org.gradle.api.Project

val Project.minSdkVersion: Int get() = getIntVersion("minSdkVersion")
val Project.compileSdkVersion: Int get() = getIntVersion("compileSdkVersion")
val Project.targetSdkVersion: Int get() = getIntVersion("targetSdkVersion")

private fun Project.getIntVersion(alias: String): Int =
    libs.findVersion(alias).get().requiredVersion.toInt()