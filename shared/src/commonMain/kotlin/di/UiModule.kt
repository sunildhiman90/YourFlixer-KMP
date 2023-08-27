package di

import downloads.DefaultDownloadsComponent
import home.DefaultHomeComponent
import homeroot.DefaultHomeRootComponent
import itemdetail.DefaultItemDetailComponent
import navigation.DefaultMainNavigationComponent
import org.koin.dsl.module
import profile.DefaultProfileComponent
import root.DefaultRootComponent
import root.RootComponent
import search.DefaultSearchComponent
import stream.DefaultStreamVideoComponent

// common to all
val commonUiModule = module {
    single {
        DefaultStreamVideoComponent.Factory(
            dispatchers = get(),
        )
    }

    single {
        DefaultHomeComponent.Factory(
            dispatchers = get(),
        )
    }

    single {
        DefaultItemDetailComponent.Factory(
            dispatchers = get(),
        )
    }

    single {
        DefaultSearchComponent.Factory(
            dispatchers = get(),
        )
    }

    single {
        DefaultDownloadsComponent.Factory(
            dispatchers = get(),
        )
    }

    single {
        DefaultProfileComponent.Factory(
            dispatchers = get(),
        )
    }
}

// decompose components, common to android and ios
val androidIosUiModule = module {


    single<RootComponent> {
        DefaultRootComponent(
            componentContext = get(),
            dispatchers = get(),
            mainNavigationComponentFactory = get(),
            streamVideoComponentFactory = get()
        )
    }

    single {
        DefaultMainNavigationComponent.Factory(
            dispatchers = get(),
            homeRootComponentFactory = get(),
            searchComponentFactory = get(),
            downloadsComponentFactory = get(),
            profileComponentFactory = get(),
        )
    }
    

    single {
        DefaultHomeRootComponent.Factory(
            dispatchers = get(),
            homeComponentFactory = get(),
            itemDetailComponentFactory = get(),
        )
    }

} + commonUiModule

