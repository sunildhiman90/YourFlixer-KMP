import org.gradle.kotlin.dsl.get

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.native.cocoapods)
    //kotlin("native.cocoapods")

    //required by decompose
    //id("kotlin-parcelize")
    // id("com.arkivanov.parcelize.darwin") // Optional, only if you need state preservation on Darwin (Apple) targets
}

kotlin {
    //targetHierarchy.default()

    androidTarget()

    jvm("desktop")

    //WebApp Step1: js target for webApp
    js(IR) {
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
            //transitiveExport = true
            export(libs.com.arkivanov.decompose.decompose)
            export(libs.com.arkivanov.essenty.lifecycle)
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
                api(libs.image.loader)

                implementation(libs.kermit)

                implementation(libs.com.arkivanov.decompose.decompose)
                implementation(libs.decompose.extensions.compose)

                // koin dependency injection
                api(libs.koin.core)

                //ktor
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.auth)
                implementation(libs.ktor.client.logging)

                //mvi kotlin
                implementation(libs.mvikotlin)
                implementation(libs.mvikotlin.main)
                implementation(libs.mvikotlin.extensions.coroutines)


                implementation(libs.kotlinx.serialization.json)
            }
        }
        val androidMain by getting {
            //required due to moko-resources issue
            dependsOn(commonMain)
            dependencies {
                api(libs.androidx.activity.compose)
                api(libs.androidx.appcompat)
                api(libs.androidx.core.ktx)
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
                implementation(libs.ktor.client.darwin)
                //we need to use api instead of implementation if we are exporting these dependencies to ios using cocoapods
                api(libs.com.arkivanov.decompose.decompose)
                api(libs.com.arkivanov.essenty.lifecycle)
                implementation(libs.decompose.extensions.compose)
                //api("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
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
                implementation(libs.ktor.client.js)
                implementation(compose.html.core)

                implementation(libs.kotlinx.html.js)
            }
        }

        //WebApp Step3: Create a simple jvm module similar to desktopApp, by copying that and name it webApp
    }
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    namespace = "com.yourflixer.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}