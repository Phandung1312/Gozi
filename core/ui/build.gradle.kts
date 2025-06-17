plugins {
    id("gozi-android-library")
    id("gozi-hilt")
    id("gozi-compose")
    id("gozi-common-dependencies")
}

android {
    namespace = "com.app.ui"
}

dependencies {
    // UI module typically has no additional dependencies beyond convention plugins
    androidTestImplementation(libs.hilt.android.testing)
    debugImplementation(libs.androidx.ui.tooling)
}