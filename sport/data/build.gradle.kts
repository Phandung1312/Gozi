plugins {
    id("gozi-android-library")
    id("gozi-hilt")
    id("gozi-common-dependencies")
}

android {
    namespace = "com.app.gozi.sport.data"
}

dependencies {
    implementation(project(":sport:domain"))
    implementation(libs.material)
}

