package root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import core.component.DeepLink
import features.downloads.DownloadsComponentFactory
import features.home.HomeComponentFactory
import features.itemdetail.ItemDetailComponentFactory
import features.profile.ProfileComponentFactory
import features.search.SearchComponentFactory
import features.stream.StreamVideoComponentFactory
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