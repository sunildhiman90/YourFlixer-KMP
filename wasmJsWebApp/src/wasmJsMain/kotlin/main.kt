import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import di.startKoinWasmJs
import kotlinx.browser.document
import root.WebDesktopRootComponent

// init koin
private val koin = startKoinWasmJs()


fun main() {

    val lifecycle = koin.get<LifecycleRegistry>()
    val root = koin.get<WebDesktopRootComponent>()

    //lifecycle.resume()
    lifecycle.attachToDocument()

    //this class support resizing which is not yet supported in skika, Though resizing is little bit slow, but good workaround as of now
    // workaround for this: https://github.com/JetBrains/skiko/issues/722
    @OptIn(ExperimentalComposeUiApi::class)
    ComposeViewport(document.body!!) {
        MainWebView(rootComponent = root)
    }

    // wasm way
    /*CanvasBasedWindow(Strings.app) {
        MainWebView(rootComponent = root)
    }*/

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

