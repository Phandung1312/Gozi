plugins {
    id("gozi-android-library")
    id("gozi-hilt")
    id("gozi-common-dependencies")
}

android {
    namespace = "com.app.gozi.food.domain"
}

dependencies {
    implementation(libs.material)
}