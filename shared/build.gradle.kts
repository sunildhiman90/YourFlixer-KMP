plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")

    //required by decompose
    id("kotlin-parcelize")
    // id("com.arkivanov.parcelize.darwin") // Optional, only if you need state preservation on Darwin (Apple) targets

    //for moko resources
    id("dev.icerock.mobile.multiplatform-resources")

}


//TODO FIX later, Directly moko resources is not working for web, We need to add webpack.config.d/moko-resources-generated.js file from shared module to webApp after first build
//for moko resources,
multiplatformResources {
    multiplatformResourcesPackage = "com.yourflixer.common" // required
    //multiplatformResourcesClassName = "SharedRes" // optional, default MR
    //multiplatformResourcesVisibility = dev.icerock.gradle.MRVisibility.Internal // optional, default Public
    //iosBaseLocalizationRegion = "en" // optional, default "en"
    //multiplatformResourcesSourceSet = "commonClientMain"  // optional, default "commonMain"
}

val decomposeVersion = extra["decompose.version.experimental"] as String
val essentyVersion = extra["essenty.version"] as String
val imageLoaderVersion = extra["image-loader.version"] as String
val kermitVersion = extra["kermit.version"] as String
val koinVersion = extra["koin.version"] as String
val ktorVersion = extra["ktor.version"] as String
val mokoResourcesVersion = extra["moko-resources.version"] as String
val mvikotlinVersion = extra["mvikotlin.version"] as String

kotlin {
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
        ios.deploymentTarget = "14.1"
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

                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                //this issue is fixed in 1.4.3-> Error loading module 'compose-instagram-clone-multiplatform'. Its dependency '@js-joda/core' was not found. Please, check whether '@js-joda/core' is loaded prior to 'compose-instagram-clone-multiplatform'
                api("io.github.qdsfdhvh:image-loader:$imageLoaderVersion")

                implementation("co.touchlab:kermit:$kermitVersion")

                //val decomposeVersion = extra["decompose.version.experimental"] as String
                implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")

                // koin dependency injection
                api("io.insert-koin:koin-core:$koinVersion")
                api("io.insert-koin:koin-test:$koinVersion")

                //ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")

                //moko resources
                api("dev.icerock.moko:resources:$mokoResourcesVersion")
                api("dev.icerock.moko:resources-compose:$mokoResourcesVersion") // for compose multiplatform
                //testImplementation("dev.icerock.moko:resources-test:$mokoResourcesVersion")

                //mvi kotlin
                implementation("com.arkivanov.mvikotlin:mvikotlin:$mvikotlinVersion")
                implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:$mvikotlinVersion")
            }
        }
        val androidMain by getting {
            //required due to moko-resources issue
            dependsOn(commonMain)
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.12.0")
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
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")
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