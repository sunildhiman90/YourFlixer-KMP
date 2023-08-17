package com.yourflixer.android.di

import org.koin.dsl.module
import utils.AppDispatchers

// native android modules
val androidModule = module {
    // singleOf(::AnyClass)
    // they can be different according to platform, so we will be passing from native side
    single { AppDispatchers() }
}