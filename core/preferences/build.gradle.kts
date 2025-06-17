plugins {
    id("gozi-android-library")
    id("gozi-hilt")
    id("gozi-common-dependencies")
}

android {
    namespace = "com.app.preferences"
}

dependencies {
    implementation(libs.material)
}