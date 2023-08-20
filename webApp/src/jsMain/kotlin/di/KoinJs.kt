package di


fun startKoinJs() = initKoin(
    //additionalModules = listOf(jsModule,JsUiModule),
    additionalModules = jsModule + jsUiModule, //plus operator generates List of modules if we add 2 modules
    enableNetworkLogs = false
).koin