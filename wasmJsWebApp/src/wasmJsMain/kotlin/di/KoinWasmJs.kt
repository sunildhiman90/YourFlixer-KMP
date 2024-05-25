package di


fun startKoinWasmJs() = initKoin(
    //additionalModules = listOf(jsModule,JsUiModule),
    additionalModules = wasmJsModule + wasmJsUiModule, //plus operator generates List of modules if we add 2 modules
    enableNetworkLogs = false
).koin