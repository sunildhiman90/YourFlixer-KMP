import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable

actual fun getPlatformName(): String = "Web"

@Composable fun MainView() = App()

//@Preview
//@Composable
//fun WebAppPreview() {
//    App()
//}