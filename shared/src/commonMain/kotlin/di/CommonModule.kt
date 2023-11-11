package di

import features.home.data.repo.HomeRepository
import org.koin.dsl.module

// common module classes will go here
fun commonModule(enableNetworkLogs: Boolean) = networkModule() + module {

    single {
        HomeRepository(get())
    }
}