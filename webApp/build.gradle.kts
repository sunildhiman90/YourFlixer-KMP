
plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}


val copyJsResources = tasks.create("copyJsResourcesWorkaround", Copy::class.java) {
    from(project(":shared").file("src/commonMain/resources"))
    into("build/processedResources/js/main")
}

afterEvaluate {
    project.tasks.getByName("jsDevelopmentExecutableCompileSync") {
        dependsOn(copyJsResources)

        //make sure to load jsPackageJson before jsDevelopmentExecutableCompileSync
        dependsOn(project.tasks.getByName("jsPackageJson"))
    }
    project.tasks.getByName("jsBrowserDevelopmentRun") {
        //make sure to load jsPackageJson before jsBrowserDevelopmentRun
        //dependsOn(project.tasks.getByName("jsNpm"))
    }
    project.tasks.getByName("jsProcessResources").finalizedBy(copyJsResources)
}

kotlin {

    js(IR) {
        moduleName = "your-flixer"
        browser {
            // Workaround for: web: Error loading module 'app-name'. Its dependency 'androidx-runtime' was not found
            // https://github.com/JetBrains/compose-multiplatform/issues/3345
            commonWebpackConfig() {
                outputFileName = "your-flixer.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting  {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.ui)
                implementation(compose.runtime)
                implementation(compose.foundation)

                //for manipulating web.dom in kotlin web
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.570")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.570")

                val decomposeVersion = extra["decompose.version.experimental"] as String
                implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")

                //for compose imageloader web support
                implementation(npm("path-browserify", "^1.0.1"))
                implementation(npm("os-browserify", "^0.3.0"))
            }
        }
    }
}

compose.experimental {
    web.application {}
}
