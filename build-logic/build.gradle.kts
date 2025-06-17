plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    compileOnly("com.android.tools.build:gradle:8.9.2")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
}

gradlePlugin {
    plugins {
        register("goziApplicationConventionPlugin") {
            id = "gozi-android-application"
            implementationClass = "GoziApplicationConventionPlugin"
        }
        register("goziLibraryConventionPlugin") {
            id = "gozi-android-library"
            implementationClass = "GoziLibraryConventionPlugin"
        }
        register("goziHiltConventionPlugin") {
            id = "gozi-hilt"
            implementationClass = "GoziHiltConventionPlugin"
        }
        register("goziComposeConventionPlugin") {
            id = "gozi-compose"
            implementationClass = "GoziComposeConventionPlugin"
        }
        register("goziCommonDependenciesConventionPlugin") {
            id = "gozi-common-dependencies"
            implementationClass = "GoziCommonDependenciesConventionPlugin"
        }
    }
}
