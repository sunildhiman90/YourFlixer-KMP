@file:Suppress("OPTIONAL_DECLARATION_USAGE_IN_NON_COMMON_SOURCE") // Workaround for KTIJ-22326

package root.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import core.ComposeScreenConfiguration
import core.LocalComposeScreenConfiguration
import core.navigation.RootDestination
import core.navigation.TopLevelDestination
import home.ui.PreviewHomeComponent
import io.github.skeptick.libres.LibresSettings
import logger.AppLogger
import navigation.PreviewMainNavigationComponent
import navigation.ui.NavContent
import root.RootComponent
import stream.ui.StreamVideoContent
import utils.AppContentType
import utils.AppNavigationType
import utils.DeviceInfo
import utils.getAppNavigationAndContentType

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {

    //LibresSettings.languageCode = "hi"

    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    lateinit var composeConfiguration: ComposeScreenConfiguration
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        composeConfiguration = ComposeScreenConfiguration(maxWidth, maxHeight)

        val childStack = component.childStack

        CompositionLocalProvider(LocalComposeScreenConfiguration provides composeConfiguration) {
            AppLogger.d("screen_width=${LocalComposeScreenConfiguration.current.width}")
            val appNavigationAndContentType = getAppNavigationAndContentType(
                DeviceInfo.calculateFromWidth(LocalComposeScreenConfiguration.current.width)
            )

            //TODO, use custom WindowSizeClass from nytimes-kmp sample
            val appNavigationType = appNavigationAndContentType.first
            val appContentType = appNavigationAndContentType.second

            AppLogger.d("appNavigationType=$appNavigationType")
            AppLogger.d("appContentType=$appContentType")

            AppContent(
                appNavigationType = appNavigationType,
                appContentType = appContentType,
                childStack = component.childStack,
                modifier = modifier,
                activeComponent = activeComponent,
                component = component,
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppContent(
    appNavigationType: AppNavigationType,
    appContentType: AppContentType,
    childStack: Value<ChildStack<*, RootComponent.RootChild>>,
    modifier: Modifier,
    activeComponent: RootComponent.RootChild,
    component: RootComponent,
) {
    val activeComponent = component.childStack.active.instance
    //val showBottomBar = component.showBottomBar.subscribeAsState()
    AppLogger.d("activeComponent1=$activeComponent")

    Children(
        stack = childStack,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is RootComponent.RootChild.MainNavChild -> NavContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
                appContentType = appContentType,
                appNavigationType = appNavigationType
            )

            is RootComponent.RootChild.StreamVideoChild -> StreamVideoContent(
                component = child.component, modifier = Modifier.fillMaxSize()
            )
        }
    }

}


//TODO
//@Preview
@Composable
internal fun RootContentPreview() {
    RootContent(PreviewRootComponent())
}

internal class PreviewRootComponent : RootComponent {
    override val childStack: Value<ChildStack<*, RootComponent.RootChild>> = MutableValue(
        ChildStack(
            configuration = Unit,
            instance = RootComponent.RootChild.MainNavChild(component = PreviewMainNavigationComponent()),
        )
    )
    override val showBottomBar: MutableValue<Boolean>
        get() = TODO("Not yet implemented")
    override var lastSelectedTabDestination: RootDestination
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun onBackPressed() {
        TODO("Not yet implemented")
    }


}

