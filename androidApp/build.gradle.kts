plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")

    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0" // this version matches your Kotlin version

}

val koinVersion = project.extra["koin.version"] as String

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.material3)

                implementation("io.insert-koin:koin-android:$koinVersion")

                val decomposeVersion = project.extra["decompose.version"] as String
                implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
                implementation("com.arkivanov.decompose:extensions-compose:$decomposeVersion")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.yourflixer.android"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "com.yourflixer.android"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // override compose compiler version for android, This is not needed in Kotlin 2.0
//    composeOptions {
//        val composeVersion = extra["compose.compiler.version"] as String
//        kotlinCompilerExtensionVersion = composeVersion
//    }

    buildFeatures.compose = true
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")

            // Enables code shrinking, obfuscation, and optimization for only
            // your project's release build type. Make sure to use a build
            // variant with `isDebuggable=false`.
            isMinifyEnabled = true

            // Enables resource shrinking, which is performed by the
            // Android Gradle plugin.
            isShrinkResources = true

            // Includes the default ProGuard rules files that are packaged with
            // the Android Gradle plugin. To learn more, go to the section about
            // R8 configuration files.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlin {
        jvmToolchain(11)
    }

    // for moko resources, locales
    bundle {
        language {
            enableSplit = false
        }
    }
}
