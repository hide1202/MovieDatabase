plugins {
    alias(libs.plugins.moviedatabase.android.library)
}

android {
    namespace = "io.viewpoint.moviedatabase.core.preferences"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.androidx.datastore.prefs)

    testImplementation(libs.test.junit)

    androidTestImplementation(project(":test:base"))
    androidTestImplementation(libs.androidTestJunit)
    androidTestImplementation(libs.androidTestEspressoCore)
    androidTestImplementation(libs.test.strikt)
}