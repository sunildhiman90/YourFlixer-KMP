@file:Suppress("OPTIONAL_DECLARATION_USAGE_IN_NON_COMMON_SOURCE") // Workaround for KTIJ-22326

package root.ui


import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import core.ComposeScreenConfiguration
import core.LocalComposeScreenConfiguration
import core.MainVerticalLazyGridScrollBar
import core.MainVerticalLazyListScrollBar
import core.MainVerticalScrollBar
import core.navigation.RootDestination
import core.navigation.TopLevelDestination
import home.ui.PreviewHomeComponent
import navigation.ui.JsNavContent
import root.JsRootComponent
import utils.AppContentType
import utils.AppNavigationType
import utils.DeviceInfo
import utils.getAppNavigationAndContentType

@Composable
fun JsRootContent(component: JsRootComponent, modifier: Modifier = Modifier) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    lateinit var composeConfiguration: ComposeScreenConfiguration
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        composeConfiguration = ComposeScreenConfiguration(maxWidth, maxHeight)

        val childStack = component.childStack

        CompositionLocalProvider(LocalComposeScreenConfiguration provides composeConfiguration) {
            println("screen_width=${LocalComposeScreenConfiguration.current.width}")
            val appNavigationAndContentType = getAppNavigationAndContentType(
                DeviceInfo.calculateFromWidth(LocalComposeScreenConfiguration.current.width)
            )

            //TODO, use custom WindowSizeClass from nytimes-kmp sample
            val appNavigationType = appNavigationAndContentType.first
            val appContentType = appNavigationAndContentType.second

            println("appNavigationType=$appNavigationType")
            println("appContentType=$appContentType")

            JsAppContent(
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
private fun JsAppContent(
    appNavigationType: AppNavigationType,
    appContentType: AppContentType,
    childStack: Value<ChildStack<*, JsRootComponent.RootChild>>,
    modifier: Modifier,
    activeComponent: JsRootComponent.RootChild,
    component: JsRootComponent,
) {
    val activeComponent = component.childStack.active.instance
    //val showBottomBar = component.showBottomBar.subscribeAsState()
    println("activeComponent1=$activeComponent")
    JsNavContent(
        component,
        modifier,
        appNavigationType,
        appContentType,
        scrollBar = { scrollState, modifier ->
            MainVerticalScrollBar(scrollState, modifier)
        },
        lazyListScrollBar = { scrollState, modifier ->
            MainVerticalLazyListScrollBar(scrollState, modifier)
        },
        lazyGridScrollBar = { scrollState, modifier ->
            MainVerticalLazyGridScrollBar(scrollState, modifier)
        }
    )

}

//TODO
//@Preview
@Composable
internal fun JsRootContentPreview() {
    JsRootContent(PreviewJsRootComponent())
}

internal class PreviewJsRootComponent : JsRootComponent {
    override val childStack: Value<ChildStack<*, JsRootComponent.RootChild>> = MutableValue(
        ChildStack(
            configuration = Unit,
            instance = JsRootComponent.RootChild.HomeNavChild(component = PreviewHomeComponent()),
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

    override fun onHomeTabClicked() {
        TODO("Not yet implemented")
    }

    override fun onSearchTabClicked() {
        TODO("Not yet implemented")
    }

    override fun onDownloadTabClicked() {
        TODO("Not yet implemented")
    }


    override fun onProfileTabClicked() {
        TODO("Not yet implemented")
    }

    override fun onAppNavigationRailItemClicked(selectedDestination: TopLevelDestination) {
        TODO("Not yet implemented")
    }

    override fun onBottomBarItemClicked(selectedDestination: TopLevelDestination) {
        TODO("Not yet implemented")
    }

}