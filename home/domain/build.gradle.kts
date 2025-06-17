plugins {
    id("gozi-android-library")
    id("gozi-common-dependencies")
}

android {
    namespace = "com.app.home.domain"
    
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.material)
}