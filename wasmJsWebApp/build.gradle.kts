import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")

    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" // this version matches your Kotlin version

}


// IT seems that in latest version of compose multiplatform, due to project structure changes, we need to copy resources to webApp/build/processedResources/js/main,
// Becoz in new project structure they are being copied to composeApp/build/processedResources/js/main and being used directly from there becoz we dont have webApp as separate module
val copyWasmJsResources = tasks.create("copyWasmJsResourcesWorkaround", Copy::class.java) {
    from(project(":shared").file("src/commonMain/composeResources"))
    into("build/processedResources/wasmJs/main")
}

afterEvaluate {

    project.tasks.getByName("wasmJsDevelopmentExecutableCompileSync") {
        dependsOn(copyWasmJsResources)

        //make sure to load jsPackageJson before jsDevelopmentExecutableCompileSync
        dependsOn(project.tasks.getByName("wasmJsPackageJson"))
    }
    project.tasks.getByName("wasmJsBrowserDevelopmentRun") {
        //make sure to load jsPackageJson before jsBrowserDevelopmentRun
        //dependsOn(project.tasks.getByName("jsNpm"))
    }
    project.tasks.getByName("wasmJsProcessResources").finalizedBy(copyWasmJsResources)

}

kotlin {

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "yourflixer"
        browser {
            commonWebpackConfig {
                outputFileName = "yourflixer.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.projectDir.path)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.ui)
                implementation(compose.runtime)
                implementation(compose.foundation)

                //for manipulating web.dom in kotlin web
                //implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.570")
                //implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.570")

                val decomposeVersion = extra["decompose.version"] as String
                implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
                implementation("com.arkivanov.decompose:extensions-compose:$decomposeVersion")

                //for compose imageloader web support
                implementation(npm("path-browserify", "^1.0.1"))
                implementation(npm("os-browserify", "^0.3.0"))
            }
        }
    }
}
//Starting from 1.6.10, Compose for Web goes to Alpha. Experimental configuration is not needed anymore.
//compose.experimental {
//    web.application {}
//}