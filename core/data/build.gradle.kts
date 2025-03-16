plugins {
    alias(libs.plugins.moviedatabase.android.library)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "io.viewpoint.moviedatabase.core.data"

    buildTypes {
        debug {
            buildConfigField("int", "DATABASE_VERSION", "1")
        }
        release {
            buildConfigField("int", "DATABASE_VERSION", "1")
        }
    }
}

dependencies {
    implementation(project(":core:common-coroutines"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:api"))

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.hilt)
    kapt(libs.hilt.kapt)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room)
    kapt(libs.androidx.room.compiler)
}
