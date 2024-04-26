import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")

    //moko resources
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":shared"))

                val coroutinesSwingVersion = extra["kotlinx.coroutines.swing"] as String
                runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-swing:$coroutinesSwingVersion")

                val decomposeVersion = extra["decompose.version"] as String
                implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
                implementation("com.arkivanov.decompose:extensions-compose:$decomposeVersion")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "KotlinMultiplatformComposeDesktopApplication"
            packageVersion = "1.0.0"
        }
    }
}


//moko resources
multiplatformResources {
    multiplatformResourcesPackage = "com.yourflixer.desktop"
}