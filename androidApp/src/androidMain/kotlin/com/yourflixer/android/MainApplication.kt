package com.yourflixer.android

import android.app.Application
import com.yourflixer.android.di.androidModule
import di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // start koin
        initKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(androidModule)
        }
    }
}