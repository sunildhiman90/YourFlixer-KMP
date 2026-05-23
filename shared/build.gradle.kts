plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKmpLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.native.cocoapods)
}

kotlin {
    android {
        namespace = "com.yourflixer.common"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        androidResources {
            enable = true
        }

        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }

    jvm("desktop")

    js(IR) {
        browser()
    }

    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "15.2"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true

            export(libs.com.arkivanov.decompose.decompose)
            export(libs.com.arkivanov.essenty.lifecycle)
        }
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

                api(libs.image.loader)

                implementation(libs.kermit)

                implementation(libs.com.arkivanov.decompose.decompose)
                implementation(libs.decompose.extensions.compose)

                api(libs.koin.core)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.auth)
                implementation(libs.ktor.client.logging)

                implementation(libs.mvikotlin)
                implementation(libs.mvikotlin.main)
                implementation(libs.mvikotlin.extensions.coroutines)

                implementation(libs.kotlinx.serialization.json)
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.androidx.activity.compose)
                api(libs.androidx.appcompat)
                api(libs.androidx.core.ktx)
            }
        }
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktor.client.darwin)
                api(libs.com.arkivanov.decompose.decompose)
                api(libs.com.arkivanov.essenty.lifecycle)
                implementation(libs.decompose.extensions.compose)
            }
        }

        val webDesktopCommonMain by creating {
            dependsOn(commonMain)
        }

        val desktopMain by getting {
            dependsOn(webDesktopCommonMain)
            dependencies {
                implementation(compose.desktop.common)
            }
        }

        val jsMain by getting {
            dependsOn(webDesktopCommonMain)
            dependencies {
                implementation(libs.ktor.client.js)
                implementation(compose.html.core)

                implementation(libs.kotlinx.html.js)
            }
        }
    }
}
