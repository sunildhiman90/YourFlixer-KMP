package root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import core.component.Component
import core.navigation.RootDestination
import core.navigation.RootScreenDestination
import navigation.MainNavigationComponent
import stream.StreamVideoComponent

interface RootComponent : Component {

    val childStack: Value<ChildStack<*, RootChild>>

    val showBottomBar: MutableValue<Boolean>

    var lastSelectedTabDestination: RootDestination

    //for custom back press
    fun onBackPressed()

    sealed class RootChild(val destination: RootDestination) {

        data class MainNavChild(
            val component: MainNavigationComponent,
        ) : RootChild(destination) {
            companion object {
                val destination = RootScreenDestination.MAIN_NAVIGATION
            }
        }


        data class StreamVideoChild(
            val component: StreamVideoComponent,
        ) : RootChild(destination) {
            companion object {
                val destination = RootScreenDestination.STREAM_VIDEO
            }

        }

    }

}