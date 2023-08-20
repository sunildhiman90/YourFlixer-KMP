package di

import DesktopDefaultRootComponent
import org.koin.dsl.module
import root.WebDesktopRootComponent

val jvmUiModule  = module {
    single<WebDesktopRootComponent> {
        DesktopDefaultRootComponent(
            componentContext = get(),
            dispatchers = get()
        )
    }

}