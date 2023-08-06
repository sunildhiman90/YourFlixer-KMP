import androidx.compose.ui.window.Window
import org.jetbrains.skiko.wasm.onWasmReady
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.webhistory.DefaultWebHistoryController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.arkivanov.essenty.lifecycle.stop
import core.component.DeepLink
import kotlinx.browser.document
import kotlinx.browser.window
import root.DefaultRootComponent
import utils.Strings
import web.dom.DocumentVisibilityState

@OptIn(ExperimentalDecomposeApi::class)
fun main() {

    val lifecycle = LifecycleRegistry()

    val root = DefaultRootComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        deepLink = DeepLink.Web(path = window.location.pathname),
        webHistoryController = DefaultWebHistoryController(),
    )

    //lifecycle.resume()
    lifecycle.attachToDocument()

    onWasmReady {

        //this class support resizing which is not yet supported in skika, Though resizing is little bit slow, but good workaround as of now
        // workaround for this: https://github.com/JetBrains/skiko/issues/722
        BrowserViewportWindow(Strings.app) {
            MainWebView(root)
        }

        // wasm way
//        CanvasBasedWindow("ImageViewer") {
//            ImageViewerWeb()
//        }
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

