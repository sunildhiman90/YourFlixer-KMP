plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    kotlin("multiplatform").apply(false)
    id("com.android.application").apply(false)
    id("com.android.library").apply(false)
    id("org.jetbrains.compose").apply(false)
}


//due to : https://github.com/JetBrains/compose-multiplatform/issues/3486
// Cross module dependency resolution failed due to signature 'org.w3c.dom.events/EventListener|2833721539368120484
//afterEvaluate {
//    project.extensions.findByType<org.jetbrains.compose.ComposeExtension>()?.also {
//        it.kotlinCompilerPlugin.set("1.5.0")
//        it.kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=1.9.10-456")
//    }
//}

//for moko resources
buildscript {

    val mokoResourcesVersion = extra["moko-resources.version"] as String

    repositories {
        gradlePluginPortal()
    }

    dependencies {
        classpath("dev.icerock.moko:resources-generator:$mokoResourcesVersion")
    }
}
