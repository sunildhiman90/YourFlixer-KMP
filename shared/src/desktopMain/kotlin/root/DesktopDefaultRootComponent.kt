import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import core.component.DeepLink
import root.WebDesktopDefaultRootComponent
import root.WebDesktopRootComponent


@OptIn(ExperimentalDecomposeApi::class)
open class DesktopDefaultRootComponent(
    componentContext: ComponentContext,
    deepLink: DeepLink = DeepLink.None,
    webHistoryController: WebHistoryController? = null,
) : WebDesktopRootComponent by WebDesktopDefaultRootComponent(
    componentContext, deepLink, webHistoryController
), ComponentContext by componentContext