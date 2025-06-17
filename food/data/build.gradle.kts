plugins {
    id("gozi-android-library")
    id("gozi-hilt")
    id("gozi-common-dependencies")
}

android {
    namespace = "com.app.gozi.food.data"
}

dependencies {
    implementation(project(":food:domain"))
    implementation(libs.material)
}