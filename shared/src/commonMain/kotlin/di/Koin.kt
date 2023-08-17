package di

import logger.AppLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

// Because in android we are calling it from Application class, but we need to use ComponentContext for all decompose components and that will be available in Activity,
// so we need to add that from activity later
fun initKoin(additionalModules: List<Module> = emptyList(), enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration() // native modules, specially from android
        modules(additionalModules + commonModule(enableNetworkLogs = enableNetworkLogs) + platformModule)
    }