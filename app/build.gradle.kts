plugins {
    id("gozi-android-application")
    id("gozi-hilt")
    id("gozi-compose")
    id("gozi-common-dependencies")
}

android {
    namespace = "com.app.gozi"

    defaultConfig {
        applicationId = "com.app.gozi"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:di"))
    implementation(project(":home:presentation"))
    implementation(project(":food:presentation"))
    implementation(project(":food:data"))
    implementation(project(":sport:presentation"))
    implementation(project(":sport:data"))
    implementation(libs.androidx.core.splashscreen)
}