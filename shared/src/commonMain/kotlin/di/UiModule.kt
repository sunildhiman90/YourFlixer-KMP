package di

import org.koin.dsl.module
import root.DefaultRootComponent
import root.RootComponent
import stream.DefaultStreamVideoComponent

val commonUiModule = module {
    single {
        DefaultStreamVideoComponent.Factory(
            dispatchers = get(),
        )
    }
}

// decompose components
val androidIosUiModule = module {

    single<RootComponent> {
        DefaultRootComponent(
            componentContext = get(),
            dispatchers = get(),
            streamVideoComponentFactory = get()
        )
    }

} + commonUiModule

