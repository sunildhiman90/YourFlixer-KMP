import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.arkivanov.essenty.lifecycle.stop
import di.startKoinJs
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady
import root.WebDesktopRootComponent
import utils.Strings
import web.dom.DocumentVisibilityState

// init koin
private val koin = startKoinJs()

@OptIn(ExperimentalDecomposeApi::class)
fun main() {

    //TODO, start koin from here in a separate method

    val lifecycle = koin.get<LifecycleRegistry>()
    val root = koin.get<WebDesktopRootComponent>()

    //lifecycle.resume()
    lifecycle.attachToDocument()

    onWasmReady {

        //this class support resizing which is not yet supported in skika, Though resizing is little bit slow, but good workaround as of now
        // workaround for this: https://github.com/JetBrains/skiko/issues/722
        BrowserViewportWindow(Strings.app) {
            MainWebView(rootComponent = root)
        }

        // wasm way
        /*CanvasBasedWindow("ImageViewer") {
            ImageViewerWeb()
        }*/
    }
}


private fun LifecycleRegistry.attachToDocument() {
    fun onVisibilityChanged() {
        if (web.dom.document.visibilityState == DocumentVisibilityState.visible) {
            resume()
        } else {
            stop()
        }
    }

    onVisibilityChanged()

    document.addEventListener(
        type = "visibilitychange",
        callback = { onVisibilityChanged() })
}

