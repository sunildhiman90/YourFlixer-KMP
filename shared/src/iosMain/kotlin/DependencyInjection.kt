import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import di.androidIosUiModule
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.dsl.module
import root.RootComponent
import utils.AppDispatchers

// for using in ios
fun initKoinIOS() = di.initKoin(additionalModules =
    module {
        single { AppDispatchers() }
        single { LifecycleRegistry() }
        single<ComponentContext> { DefaultComponentContext(lifecycle = get<LifecycleRegistry>()) }
    } + androidIosUiModule
)


// Provide extensions to use in ios side in swift file
val Koin.rootComponent: RootComponent
    get() {
        return get()
    }

val Koin.lifecycleRegistry: LifecycleRegistry
    get() = get()


// Currently we are using way mentioned above by providing extensions to use in ios side in swift file
// But This way can be used in ios side as alternative way of using directly koin.get() in swift side
@OptIn(BetaInteropApi::class)
fun <T> Koin.getDependency(objCClass: ObjCClass): T? = getOriginalKotlinClass(objCClass)?.let {
    getDependency(it)
}