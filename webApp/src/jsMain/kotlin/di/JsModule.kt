package di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.webhistory.DefaultWebHistoryController
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import core.component.DeepLink
import kotlinx.browser.window
import org.koin.core.qualifier.named
import org.koin.dsl.module
import utils.AppDispatchers

@OptIn(ExperimentalDecomposeApi::class)
val jsModule = module {
    single { AppDispatchers() }
    single { LifecycleRegistry() }
    single<ComponentContext> { DefaultComponentContext(lifecycle = get<LifecycleRegistry>()) }
    single<WebHistoryController> { DefaultWebHistoryController() }
    single(named("location_pathname")) { window.location.pathname }
    single<DeepLink> { DeepLink.Web(get(named("location_pathname"))) }
}