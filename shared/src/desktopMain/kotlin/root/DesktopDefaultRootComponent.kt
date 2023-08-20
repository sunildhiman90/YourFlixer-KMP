import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import core.component.DeepLink
import root.WebDesktopDefaultRootComponent
import root.WebDesktopRootComponent
import utils.AppDispatchers


@OptIn(ExperimentalDecomposeApi::class)
open class DesktopDefaultRootComponent(
    componentContext: ComponentContext,
    dispatchers: AppDispatchers
) : WebDesktopRootComponent by WebDesktopDefaultRootComponent(
    componentContext, deepLink = DeepLink.None, webHistoryController = null,
    dispatchers
), ComponentContext by componentContext