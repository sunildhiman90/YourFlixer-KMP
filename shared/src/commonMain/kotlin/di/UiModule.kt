package di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import features.home.HomeComponentFactory
import features.homeroot.DefaultHomeRootComponent
import features.itemdetail.ItemDetailComponentFactory
import navigation.DefaultMainNavigationComponent
import org.koin.dsl.module
import features.profile.ProfileComponentFactory
import root.DefaultRootComponent
import root.RootComponent
import features.search.SearchComponentFactory
import features.stream.StreamVideoComponentFactory


// common to all, normal injection was not working in web (becoz Type is same for all Factory, We should rename each factory name, for example we did in HomeComponent as HomeComponentFactory),
// so thats why we created separate named Factories
val commonUiModule = module {

    single<StoreFactory> {
        DefaultStoreFactory()
    }

    single {
        StreamVideoComponentFactory(
            dispatchers = get(),
        )
    }

    single {
        HomeComponentFactory(
            dispatchers = get(),
            storeFactory = get(),
            homeRepository = get()
        )
    }

    single {
        ItemDetailComponentFactory(
            dispatchers = get(),
        )
    }

    single {
        SearchComponentFactory(
            dispatchers = get(),
        )
    }

    single {
        features.downloads.DownloadsComponentFactory(
            dispatchers = get(),
        )
    }

    single {
        ProfileComponentFactory(
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
