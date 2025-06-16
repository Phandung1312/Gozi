pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Gozi"
include(":app")
include(":core")
include(":core:network")
include(":core:di")
include(":core:database")
include(":core:preferences")
include(":core:utils")
include(":core:ui")
include(":home")
include(":home:data")
include(":home:domain")
include(":home:presentation")
include(":food")
include(":food:presentation")
include(":food:data")
include(":food:domain")
include(":sport")
include(":sport:presentation")
include(":sport:data")
include(":sport:domain")
