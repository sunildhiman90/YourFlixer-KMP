package di

import DesktopDefaultRootComponent
import com.arkivanov.decompose.ExperimentalDecomposeApi
import org.koin.dsl.module
import root.WebDesktopRootComponent

@OptIn(ExperimentalDecomposeApi::class)
val jvmUiModule = module {
    single<WebDesktopRootComponent> {
        DesktopDefaultRootComponent(
            componentContext = get(),
            dispatchers = get(),
            deepLink = get(),
            webHistoryController = null,
            homeComponentFactory = get(),
            searchComponentFactory = get(),
            downloadsComponentFactory = get(),
            profileComponentFactory = get(),
            streamVideoComponentFactory = get(),
            itemDetailComponentFactory = get(),
        )
    }
} + commonUiModule