package root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import core.component.DeepLink
import downloads.DownloadsComponentFactory
import home.HomeComponentFactory
import itemdetail.ItemDetailComponentFactory
import profile.ProfileComponentFactory
import search.SearchComponentFactory
import stream.StreamVideoComponentFactory
import utils.AppDispatchers


//TODO, add AppLogger in koin and setup Kermit also from koin
@OptIn(ExperimentalDecomposeApi::class)
open class JsDefaultRootComponent(
    componentContext: ComponentContext,
    deepLink: DeepLink,
    webHistoryController: WebHistoryController?,
    dispatchers: AppDispatchers,
    homeComponentFactory: HomeComponentFactory,
    searchComponentFactory: SearchComponentFactory,
    downloadsComponentFactory: DownloadsComponentFactory,
    profileComponentFactory: ProfileComponentFactory,
    streamVideoComponentFactory: StreamVideoComponentFactory,
    itemDetailComponentFactory: ItemDetailComponentFactory,
) : WebDesktopRootComponent by WebDesktopDefaultRootComponent(
    componentContext,
    deepLink,
    webHistoryController,
    dispatchers,
    homeComponentFactory,
    searchComponentFactory,
    downloadsComponentFactory,
    profileComponentFactory,
    streamVideoComponentFactory,
    itemDetailComponentFactory
), ComponentContext by componentContext