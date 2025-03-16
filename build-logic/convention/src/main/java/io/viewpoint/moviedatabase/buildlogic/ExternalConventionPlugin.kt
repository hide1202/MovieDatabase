package io.viewpoint.moviedatabase.buildlogic

import io.viewpoint.moviedatabase.buildlogic.extensions.Externals
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.FileInputStream
import java.util.Properties

class ExternalConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        Externals.apiKey = try {
            val externalFile = target.rootProject.file("external.properties")
            val externalProperties = Properties()
            externalProperties.load(FileInputStream(externalFile))

            externalProperties.getProperty("movie.database.api.key")
        } catch (t: Throwable) {
            "DEFAULT_API_KEY"
        }
    }
}

