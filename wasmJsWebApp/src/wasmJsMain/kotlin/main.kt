import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import di.startKoinJs
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady
import root.WebDesktopRootComponent
import utils.Strings

// init koin
private val koin = startKoinWasmJs()


fun main() {

    //TODO, start koin from here in a separate method

    val lifecycle = koin.get<LifecycleRegistry>()
    val root = koin.get<WebDesktopRootComponent>()

    //lifecycle.resume()
    lifecycle.attachToDocument()

    onWasmReady {

        //this class support resizing which is not yet supported in skika, Though resizing is little bit slow, but good workaround as of now
        // workaround for this: https://github.com/JetBrains/skiko/issues/722
        @OptIn(ExperimentalComposeUiApi::class)
        CanvasBasedWindow(Strings.app, canvasElementId = "ComposeTarget") { // now from compose 1.5.10,  we dont need custom BrowserViewportWindow
            MainWebView(rootComponent = root)
        }

        // wasm way
        /*CanvasBasedWindow(Strings.app) {
            MainWebView(rootComponent = root)
        }*/
    }
}


private fun LifecycleRegistry.attachToDocument() {
    fun onVisibilityChanged() {
        //TODO, need to check this logic
//        if (document.visibilityState == DocumentVisibilityState.visible) {
//            resume()
//        } else {
//            stop()
//        }
    }

    onVisibilityChanged()

    document.addEventListener(
        type = "visibilitychange",
        callback = { onVisibilityChanged() })
}

