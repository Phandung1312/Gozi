plugins {
    id("gozi-android-library")
    id("gozi-hilt")
    id("gozi-compose")
    id("gozi-common-dependencies")
}

android {
    namespace = "com.app.gozi.sport.presentation"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":sport:domain"))
    implementation(project(":sport:data"))
}
