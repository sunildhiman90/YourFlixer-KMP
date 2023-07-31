import androidx.compose.ui.window.Window
import org.jetbrains.skiko.wasm.onWasmReady

fun main()  {

    onWasmReady {
        BrowserViewportWindow("Compose Multiplatform App Template") {
            App()
        }
    }

}