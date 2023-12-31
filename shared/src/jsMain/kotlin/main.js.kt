import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import di.initKoin
import root.WebDesktopRootComponent
import root.ui.JsRootContent
import root.ui.PreviewWebDesktopRootComponent
import utils.AppPlatform



actual fun getPlatformName(): String =  AppPlatform.WEB.name

@Composable
fun MainWebView(rootComponent: WebDesktopRootComponent) {
    MaterialTheme {
        JsRootContent(rootComponent)
    }
}

@Preview
@Composable
fun WebAppPreview() {
    MainWebView(PreviewWebDesktopRootComponent())
}