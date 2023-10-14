
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import co.touchlab.kermit.CommonWriter
import logger.AppLogger
import platform.UIKit.UIViewController
import root.RootComponent
import root.ui.RootContent
import utils.AppPlatform

actual fun getPlatformName(): String = AppPlatform.IOS.name

fun MainViewController(
    topSafeArea: Float,
    bottomSafeArea: Float,
    root: RootComponent
): UIViewController {
    // For ios, just pass CommonWriter becoz in case of platformLogWriter, Kmm is not pulling logs from Apple platform logs
    // https://github.com/touchlab/Kermit/issues/327
    AppLogger.setup(CommonWriter())
    return ComposeUIViewController {
        val density = LocalDensity.current

        val topSafeAreaDp = with(density) { topSafeArea.toDp() }
        val bottomSafeAreaDp = with(density) { bottomSafeArea.toDp() }
        val safeArea = PaddingValues(top = topSafeAreaDp + 32.dp/*, bottom = bottomSafeAreaDp*/)

        //this will be used from iosApp.swift file
//        val lifecycle = LifecycleRegistry()
//        val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)
//        val root = RootComponentImpl(
//            componentContext = rootComponentContext
//        )

        MaterialTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                //.padding(safeArea),
            ) {
                RootContent(component = root)
            }
        }

    }
}