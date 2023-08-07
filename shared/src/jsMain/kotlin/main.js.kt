import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import root.JsRootComponent
import root.RootComponent
import root.ui.JsRootContent
import root.ui.PreviewJsRootComponent
import root.ui.PreviewRootComponent
import root.ui.RootContent
import utils.AppPlatform

actual fun getPlatformName(): String =  AppPlatform.WEB.name

@Composable
fun MainWebView(rootComponent: JsRootComponent) {
    MaterialTheme {
        JsRootContent(rootComponent)
    }
}

@Preview
@Composable
fun WebAppPreview() {
    MainWebView(PreviewJsRootComponent())
}