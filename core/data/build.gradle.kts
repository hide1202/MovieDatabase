import io.viewpoint.moviedatabase.buildlogic.extensions.Externals

plugins {
    alias(libs.plugins.moviedatabase.android.library)
    alias(libs.plugins.moviedatabase.jvm.external)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "io.viewpoint.moviedatabase.core.data"

    buildTypes {
        debug {
            buildConfigField("String", "API_KEY", "\"${Externals.apiKey}\"")
            buildConfigField("int", "DATABASE_VERSION", "1")
        }
        release {
            buildConfigField("String", "API_KEY", "\"${Externals.apiKey}\"")
            buildConfigField("int", "DATABASE_VERSION", "1")
        }
    }
}

dependencies {
    implementation(project(":core:common-coroutines"))
    implementation(project(":core:domain"))
    implementation(project(":core:api"))

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.hilt)
    kapt(libs.hilt.kapt)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room)
    kapt(libs.androidx.room.compiler)

    implementation(libs.timber)

    implementation(libs.okhttp)

    // test
    testImplementation(project(":test:mock"))
    testImplementation(libs.test.junit)
    testImplementation(libs.test.strikt)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.mockk.agent.jvm)
    testImplementation(libs.moshi)
    testImplementation(libs.test.coroutine.test)

    // flipper (don't use a external gradle file, because of build types)
    debugImplementation("com.facebook.flipper:flipper:0.98.0")
    debugImplementation("com.facebook.soloader:soloader:0.10.1")
    debugImplementation("com.facebook.flipper:flipper-network-plugin:0.98.0")

    releaseImplementation("com.facebook.flipper:flipper-noop:0.98.0")
}
