package di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import core.component.DeepLink
import org.koin.core.qualifier.named
import org.koin.dsl.module
import tryRestoreStateFromFile
import utils.AppDispatchers

const val SAVED_STATE_FILE_NAME = "saved_state.dat"

val jvmModule = module {
    single { AppDispatchers() }
    single { LifecycleRegistry() }
    single(named("SAVED_STATE_FILE_NAME")) { SAVED_STATE_FILE_NAME }
    single { StateKeeperDispatcher(tryRestoreStateFromFile()) }
    single<ComponentContext> { DefaultComponentContext(lifecycle = get<LifecycleRegistry>(), stateKeeper = get<StateKeeperDispatcher>()) }
    single<DeepLink> { DeepLink.None }
}