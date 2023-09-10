plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    kotlin("multiplatform").apply(false)
    id("com.android.application").apply(false)
    id("com.android.library").apply(false)
    id("org.jetbrains.compose").apply(false)
}

// for libres
buildscript {
    //val libresVersion = extra["libres.version"] as String
    val libresVersion = project.properties["libres.version"] as String

    dependencies {
        classpath("io.github.skeptick.libres:gradle-plugin:$libresVersion")
    }
}