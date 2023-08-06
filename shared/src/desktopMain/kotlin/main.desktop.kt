import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import root.RootComponent
import root.ui.PreviewRootComponent
import root.ui.RootContent
import utils.AppPlatform

actual fun getPlatformName(): String = AppPlatform.DESKTOP.name

@Composable
fun MainView(root: RootComponent) {
    MaterialTheme {
        RootContent(root)
    }
}

@Preview
@Composable
fun AppPreview() {
    MainView(PreviewRootComponent())
}