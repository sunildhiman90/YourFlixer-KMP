import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import root.RootComponent
import root.ui.PreviewRootComponent
import root.ui.RootContent
import utils.AppPlatform

actual fun getPlatformName(): String =  AppPlatform.WEB.name

@Composable
fun MainWebView(rootComponent: RootComponent) {
    MaterialTheme {
        RootContent(rootComponent)
    }
}

@Preview
@Composable
fun WebAppPreview() {
    MainWebView(PreviewRootComponent())
}