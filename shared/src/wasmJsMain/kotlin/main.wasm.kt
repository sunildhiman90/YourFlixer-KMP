import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import root.WebDesktopRootComponent
import root.ui.PreviewWebDesktopRootComponent
import root.ui.WasmRootContent
import utils.AppPlatform



actual fun getPlatformName(): String =  AppPlatform.WEB.name

@Composable
fun MainWebView(rootComponent: WebDesktopRootComponent) {
    MaterialTheme {
        WasmRootContent(rootComponent)
    }
}

@Preview
@Composable
fun WebAppPreview() {
    MainWebView(PreviewWebDesktopRootComponent())
}