package di

import com.arkivanov.decompose.ExperimentalDecomposeApi
import features.downloads.DownloadsComponentFactory
import features.home.HomeComponentFactory
import features.itemdetail.ItemDetailComponentFactory
import org.koin.dsl.module
import features.profile.ProfileComponentFactory
import root.WebDesktopRootComponent
import features.search.SearchComponentFactory
import features.stream.StreamVideoComponentFactory
import root.WasmJsDefaultRootComponent

@OptIn(ExperimentalDecomposeApi::class)
val wasmJsUiModule = module {


    single<WebDesktopRootComponent> {

        // Currently DI is not working for these if using without named, getting this error: homeComponentFactory_1.create_esiqto_k$ is not a function
        // so using them with named option
        val homeComponentFactory = get<HomeComponentFactory>()
        val searchComponentFactory = get<SearchComponentFactory>()
        val downloadsComponentFactory = get<DownloadsComponentFactory>()
        val profileComponentFactory = get<ProfileComponentFactory>()
        val streamVideoComponentFactory = get<StreamVideoComponentFactory>()
        val itemDetailComponentFactory = get<ItemDetailComponentFactory>()


        //manually
        //val homeComponentFactory = HomeComponentFactory(dispatchers = get())
        //val searchComponentFactory = SearchComponentFactory(dispatchers = get())
        //val downloadsComponentFactory = DownloadsComponentFactory(dispatchers = get())
        //val profileComponentFactory = ProfileComponentFactory(dispatchers = get())
        //val streamVideoComponentFactory = StreamVideoComponentFactory(dispatchers = get())
        //val itemDetailComponentFactory = ItemDetailComponentFactory(dispatchers = get())

        WasmJsDefaultRootComponent(
            componentContext = get(),
            deepLink = get(),
            webHistoryController = get(),
            dispatchers = get(),
            homeComponentFactory = homeComponentFactory,
            searchComponentFactory = searchComponentFactory,
            downloadsComponentFactory = downloadsComponentFactory,
            profileComponentFactory = profileComponentFactory,
            streamVideoComponentFactory = streamVideoComponentFactory,
            itemDetailComponentFactory = itemDetailComponentFactory,
        )
    }
} + commonUiModule