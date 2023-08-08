package root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import core.component.DeepLink


@OptIn(ExperimentalDecomposeApi::class)
open class JsDefaultRootComponent(
    componentContext: ComponentContext,
    deepLink: DeepLink,
    webHistoryController: WebHistoryController?,
) : WebDesktopRootComponent by WebDesktopDefaultRootComponent(
    componentContext, deepLink, webHistoryController
), ComponentContext by componentContext