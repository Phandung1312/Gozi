plugins {
    id("gozi-android-library")
    id("gozi-common-dependencies")
    id("gozi-hilt")
}

android {
    namespace = "com.app.utils"
    
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}