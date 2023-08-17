package root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import core.component.DeepLink
import utils.AppDispatchers


//TODO, add AppLogger in koin and setup Kermit also from koin
@OptIn(ExperimentalDecomposeApi::class)
open class JsDefaultRootComponent(
    componentContext: ComponentContext,
    deepLink: DeepLink,
    webHistoryController: WebHistoryController?,
    dispatchers: AppDispatchers
) : WebDesktopRootComponent by WebDesktopDefaultRootComponent(
    componentContext, deepLink, webHistoryController, dispatchers
), ComponentContext by componentContext