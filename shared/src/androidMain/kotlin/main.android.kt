
import androidx.compose.runtime.Composable
import root.RootComponent
import root.ui.RootContent
import utils.AppPlatform

actual fun getPlatformName(): String = AppPlatform.ANDROID.name

@Composable
fun MainView(root: RootComponent) {
    RootContent(root)
}
