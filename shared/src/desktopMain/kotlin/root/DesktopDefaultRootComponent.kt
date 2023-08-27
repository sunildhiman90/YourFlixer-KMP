import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import core.component.DeepLink
import downloads.DownloadsComponentFactory
import home.HomeComponentFactory
import itemdetail.ItemDetailComponentFactory
import profile.ProfileComponentFactory
import root.WebDesktopDefaultRootComponent
import root.WebDesktopRootComponent
import search.SearchComponentFactory
import stream.StreamVideoComponentFactory
import utils.AppDispatchers


@OptIn(ExperimentalDecomposeApi::class)
open class DesktopDefaultRootComponent(
    componentContext: ComponentContext,
    dispatchers: AppDispatchers,
    deepLink: DeepLink,
    webHistoryController: WebHistoryController?,
    private val homeComponentFactory: HomeComponentFactory,
    private val searchComponentFactory: SearchComponentFactory,
    private val downloadsComponentFactory: DownloadsComponentFactory,
    private val profileComponentFactory: ProfileComponentFactory,
    private val streamVideoComponentFactory: StreamVideoComponentFactory,
    private val itemDetailComponentFactory: ItemDetailComponentFactory,
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