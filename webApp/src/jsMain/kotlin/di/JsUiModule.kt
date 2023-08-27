package di

import org.koin.dsl.module
import root.JsDefaultRootComponent
import root.WebDesktopRootComponent

val jsUiModule = module {

    single<WebDesktopRootComponent> {
        JsDefaultRootComponent(
            componentContext = get(),
            deepLink = get(),
            webHistoryController = get(),
            dispatchers = get()
        )
    }
} + commonUiModule