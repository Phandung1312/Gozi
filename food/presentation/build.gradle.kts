plugins {
    id("gozi-android-library")
    id("gozi-hilt")
    id("gozi-compose")
    id("gozi-common-dependencies")
}

android {
    namespace = "com.app.gozi.food.presentation"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":food:domain"))
    implementation(project(":food:data"))
}
