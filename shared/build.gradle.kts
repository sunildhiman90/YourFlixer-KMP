import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")

    //required by decompose
    //id("kotlin-parcelize")
    // id("com.arkivanov.parcelize.darwin") // Optional, only if you need state preservation on Darwin (Apple) targets

    kotlin("plugin.serialization") version "2.0.0"

    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" // this version matches your Kotlin version

}

val decomposeVersion = extra["decompose.version"] as String
val essentyVersion = extra["essenty.version"] as String
val imageLoaderVersion = extra["image-loader.version"] as String
val kermitVersion = extra["kermit.version"] as String
val koinVersion = extra["koin.version"] as String
//val ktorVersion = extra["ktor.version"] as String
val ktorWasmVersion = extra["ktor.wasm.version"] as String
val mvikotlinVersion = extra["mvikotlin.version"] as String
val kotlinxSerializationVersion = extra["kotlinx-serialization.version"] as String

kotlin {
    //targetHierarchy.default()

    androidTarget()

    jvm("desktop")

    //WebApp Step1: js target for webApp
    js(IR) {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "15.2" //This one need to change to 15.2 from 14.1 for using composeResources and same in shared.podspec
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true

            //export decompose libraries to ios side, becoz we are declaring RootComponent there
            transitiveExport = true
            export("com.arkivanov.decompose:decompose:$decomposeVersion")
            export("com.arkivanov.essenty:lifecycle:$essentyVersion")
        }

        //its not needed in compose 1.5.1
        //extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(compose.animation)
                implementation(compose.materialIconsExtended)

                implementation(compose.components.resources)

                //this issue is fixed in 1.4.3-> Error loading module 'compose-instagram-clone-multiplatform'. Its dependency '@js-joda/core' was not found. Please, check whether '@js-joda/core' is loaded prior to 'compose-instagram-clone-multiplatform'
                api("io.github.qdsfdhvh:image-loader:$imageLoaderVersion")

                implementation("co.touchlab:kermit:$kermitVersion")

                //val decomposeVersion = extra["decompose.version.experimental"] as String
                implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
                implementation("com.arkivanov.decompose:extensions-compose:$decomposeVersion")

                // koin dependency injection
                api("io.insert-koin:koin-core:$koinVersion")
                api("io.insert-koin:koin-test:$koinVersion")

                //ktor
                implementation("io.ktor:ktor-client-core:$ktorWasmVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorWasmVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorWasmVersion")
                implementation("io.ktor:ktor-client-auth:$ktorWasmVersion") // ktor auth

                implementation("io.ktor:ktor-client-logging:$ktorWasmVersion")

                //mvi kotlin
                implementation("com.arkivanov.mvikotlin:mvikotlin:$mvikotlinVersion")
                implementation("com.arkivanov.mvikotlin:mvikotlin-main:$mvikotlinVersion")
                implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:$mvikotlinVersion")


                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
            }
        }
        val androidMain by getting {
            //required due to moko-resources issue
            dependsOn(commonMain)
            dependencies {
                api("androidx.activity:activity-compose:1.9.0")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.13.0")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                //we need to use api instead of implementation if we are exporting these dependencies to ios using cocoapods
                api("com.arkivanov.decompose:decompose:$decomposeVersion")
                api("com.arkivanov.essenty:lifecycle:$essentyVersion")
                implementation("com.arkivanov.decompose:extensions-compose:$decomposeVersion")
            }
        }

        // Common code for components for web and desktop, otherwise we need to repeat some code at both places related to scrollbar and RootComponents
        val webDesktopCommonMain by creating {
            dependsOn(commonMain)
        }

        val desktopMain by getting {
            dependsOn(webDesktopCommonMain)
            dependencies {
                implementation(compose.desktop.common)
            }
        }

        //WebApp Step2
        val jsMain by getting {
            dependsOn(webDesktopCommonMain)
            dependencies {
                implementation(compose.html.core)

                implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.11.0")
            }
        }

        val wasmJsMain by getting {
            //dependsOn(webDesktopCommonMain)
            dependencies {
                //implementation(compose.html.core)

                //implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.11.0")
            }
        }

        //WebApp Step3: Create a simple jvm module similar to desktopApp, by copying that and name it webApp
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.yourflixer.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}