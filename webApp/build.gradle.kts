
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
        moduleName = "compose-multiplatform-app-template"
        browser {
            // Workaround for: web: Error loading module 'app-name'. Its dependency 'androidx-runtime' was not found
            // https://github.com/JetBrains/compose-multiplatform/issues/3345
            commonWebpackConfig() {
                outputFileName = "compose-multiplatform-app-template.js"
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
            }
        }
    }
}

compose.experimental {
    web.application {}
}
