package com.yourflixer.android

import MainView
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import di.androidIosUiModule
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import root.RootComponent
import theme.YourFlixerTheme

class MainActivity : AppCompatActivity(), KoinComponent {

    //Init koin here, because defaultComponentContext will be available in activity
    private val modules =
        module {
            single<ComponentContext> { defaultComponentContext() }
        } + androidIosUiModule
    // Adding uiModule which includes decompose components after initializing ComponentContext

    init {
        // load koin modules after koin have been started
        loadKoinModules(modules)
    }

    private val rootComponent: RootComponent by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            YourFlixerTheme {
                //For setting statusBarColor and navigationBarColor colors, and systemUiVisibility for light status bar, icons
                window.statusBarColor = MaterialTheme.colorScheme.surface.toArgb()
                //window.navigationBarColor = MaterialTheme.colorScheme.surface.toArgb()
                window.navigationBarColor =
                    MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                        .toArgb() //same as bottom navigation bar color

                @Suppress("DEPRECATION")
                if (MaterialTheme.colorScheme.surface.luminance() > 0.5f) {
                    window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }

                @Suppress("DEPRECATION")
                if (MaterialTheme.colorScheme.surface.luminance() > 0.5f) {
                    window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }

                MainView(rootComponent)
            }
        }
    }

    override fun onDestroy() {
        unloadKoinModules(modules)
        super.onDestroy()
    }
}