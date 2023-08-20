package di

fun startKoinJvm() = initKoin(
    //additionalModules = listOf(jvmModule,jvmUiModule),
    additionalModules = jvmModule + jvmUiModule, //plus operator generates List of modules if we add 2 modules
    enableNetworkLogs = false
).koin