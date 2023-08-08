import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import root.RootComponent
import root.WebDesktopRootComponent
import root.ui.DesktopRootContent
import root.ui.PreviewDesktopRootComponent
import root.ui.PreviewRootComponent
import root.ui.RootContent
import utils.AppPlatform

actual fun getPlatformName(): String = AppPlatform.DESKTOP.name


// This is for if you want to use single navigation with ScrollBar support, it will not provide nested navigation
@Composable
fun MainViewWebDesktopCommonMain(root: WebDesktopRootComponent) {
    MaterialTheme {
        DesktopRootContent(root)
    }
}

@Preview
@Composable
fun AppPreviewWebDesktopCommonMain() {
    MainViewWebDesktopCommonMain(PreviewDesktopRootComponent())
}


// This is for if you want to use nested navigation as well in desktop same as that of mobile android and ios,
// but currently it does not support scrollbars
@Composable
fun MainViewCommonMain(root: RootComponent) {
    MaterialTheme {
        RootContent(root)
    }
}

@Preview
@Composable
fun AppPreviewCommonMain() {
    MainViewCommonMain(PreviewRootComponent())
}