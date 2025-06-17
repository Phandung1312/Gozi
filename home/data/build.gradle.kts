plugins {
    id("gozi-android-library")
    id("gozi-common-dependencies")
}

android {
    namespace = "com.app.home.data"
}

dependencies {
    implementation(libs.material)
}