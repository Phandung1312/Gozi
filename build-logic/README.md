# Build Logic Convention Plugins

This module contains convention plugins for the Gozi project to standardize build configurations and dependencies across all modules.

## Available Convention Plugins

### 1. gozi-android-application
For Android application modules (like `:app`).

**Usage:**
```kotlin
plugins {
    id("gozi-android-application")
}
```

**What it provides:**
- Android Application plugin configuration
- Kotlin Android plugin
- Kotlin Compose plugin
- Common Android configuration (compileSdk, minSdk, targetSdk)
- Compose build features enabled
- Java 11 compatibility
- Release build type configuration

### 2. gozi-android-library
For Android library modules.

**Usage:**
```kotlin
plugins {
    id("gozi-android-library")
}
```

**What it provides:**
- Android Library plugin configuration
- Kotlin Android plugin
- Common Android configuration (compileSdk, minSdk)
- Java 11 compatibility
- Release build type configuration

### 3. gozi-hilt
For modules that use Hilt dependency injection.

**Usage:**
```kotlin
plugins {
    id("gozi-hilt")
}
```

**What it provides:**
- Hilt Android plugin
- KSP plugin for annotation processing
- Hilt dependencies (runtime, compiler, testing)

### 4. gozi-compose
For modules that use Jetpack Compose.

**Usage:**
```kotlin
plugins {
    id("gozi-compose")
}
```

**What it provides:**
- Kotlin Compose plugin
- Compose build features
- Compose BOM and core dependencies
- Compose UI, Material3, Navigation, ViewModel dependencies
- Debug and testing dependencies

### 5. gozi-common-dependencies
For modules that need common Android dependencies.

**Usage:**
```kotlin
plugins {
    id("gozi-common-dependencies")
}
```

**What it provides:**
- Core Android KTX dependencies
- Lifecycle runtime KTX
- Common testing dependencies (JUnit, Espresso)

## Example Usage

### For the main app module:
```kotlin
plugins {
    id("gozi-android-application")
    id("gozi-hilt")
    id("gozi-compose")
}
```

### For a library module with Compose:
```kotlin
plugins {
    id("gozi-android-library")
    id("gozi-common-dependencies")
    id("gozi-compose")
}
```

### For a library module with Hilt but no Compose:
```kotlin
plugins {
    id("gozi-android-library")
    id("gozi-common-dependencies")
    id("gozi-hilt")
}
```

### For a simple library module:
```kotlin
plugins {
    id("gozi-android-library")
    id("gozi-common-dependencies")
}
```

## Benefits

1. **Consistency**: All modules use the same base configuration
2. **Maintainability**: Changes to common configuration only need to be made in one place
3. **Reduced Boilerplate**: Less repetitive build.gradle.kts code across modules
4. **Standardization**: Enforces project-wide standards for SDK versions, Java compatibility, etc.
