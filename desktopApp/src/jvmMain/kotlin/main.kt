import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.defaultScrollbarStyle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.parcelable.ParcelableContainer
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import core.LocalDimensions
import di.startKoinJvm
import org.koin.core.qualifier.named
import root.WebDesktopRootComponent
import utils.Strings
import utils.dimens.Dimensions
import utils.dimens.compactDimensions
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

// init koin
private val koin = startKoinJvm()
val savedStateFileName = koin.get<String>(named("SAVED_STATE_FILE_NAME"))

@OptIn(ExperimentalDecomposeApi::class)
fun main() {

    //TODO, try with coroutines instead of reaktive
    //overrideSchedulers(main = Dispatchers.Main::asScheduler)
    val lifecycle = koin.get<LifecycleRegistry>()
    val rootWebDesktopCommonMain = koin.get<WebDesktopRootComponent>()
    val stateKeeper = koin.get<StateKeeperDispatcher>()

//    val rootWebDesktopCommonMain =
//        runOnUiThread {
//            DesktopDefaultRootComponent(
//                componentContext = DefaultComponentContext(
//                    lifecycle = lifecycle,
//                    stateKeeper = stateKeeper,
//                ),
//            )
//        }


    application {
        val windowState = rememberWindowState()

        LifecycleController(lifecycle, windowState)

        var isCloseRequested by remember { mutableStateOf(false) }

        Window(
            onCloseRequest = { isCloseRequested = true },
            state = windowState,
            title = Strings.app
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                CompositionLocalProvider(LocalScrollbarStyle provides defaultScrollbarStyle()) {

                    MainViewWebDesktopCommonMain(rootWebDesktopCommonMain)

                    // For CommonMain layouts with nested navigation same as that of mobile
                    //MainViewCommonMain(rootCommonMain)
                }
            }

            if (isCloseRequested) {
                SaveStateDialog(
                    onSaveState = { saveStateToFile(stateKeeper.save()) },
                    onExitApplication = ::exitApplication,
                    onDismiss = { isCloseRequested = false },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SaveStateDialog(
    onSaveState: () -> Unit,
    onExitApplication: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(compactDimensions.halfPadding),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(onClick = onDismiss) {
                    Text(text = "Cancel")
                }

                TextButton(onClick = onExitApplication) {
                    Text(text = "No")
                }

                TextButton(
                    onClick = {
                        onSaveState()
                        onExitApplication()
                    }
                ) {
                    Text(text = "Yes")
                }
            }
        },
        title = { Text(text = Strings.app) },
        text = { Text(text = "Do you want to save the application's state?") },
        modifier = Modifier.width(400.dp),
    )
}


private fun saveStateToFile(state: ParcelableContainer) {
    ObjectOutputStream(File(savedStateFileName).outputStream()).use { output ->
        output.writeObject(state)
    }
}

fun tryRestoreStateFromFile(): ParcelableContainer? =
    File(savedStateFileName).takeIf(File::exists)?.let { file ->
        try {
            ObjectInputStream(file.inputStream()).use(ObjectInputStream::readObject) as ParcelableContainer
        } catch (e: Exception) {
            null
        } finally {
            file.delete()
        }
    }