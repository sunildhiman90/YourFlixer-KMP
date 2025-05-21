rootProject.name = "Your-Flixer"
// project name cannot contain spaces

include(":androidApp")
include(":shared")
include(":desktopApp")

//WebApp Step4
include(":webApp")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        mavenCentral()
        //due to web app issue: https://github.com/JetBrains/compose-multiplatform/issues/3486
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev/")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
