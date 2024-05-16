@file:Suppress("OPTIONAL_DECLARATION_USAGE_IN_NON_COMMON_SOURCE") // Workaround for KTIJ-22326

package root.ui


import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import core.ComposeScreenConfiguration
import core.LocalComposeScreenConfiguration
import core.LocalDimensions
import core.MainVerticalLazyGridScrollBar
import core.MainVerticalLazyListScrollBar
import core.MainVerticalScrollBar
import core.navigation.RootDestination
import core.navigation.TopLevelDestination
import features.home.ui.PreviewHomeComponent
import logger.AppLogger
import navigation.ui.JsNavContent
import root.WebDesktopRootComponent
import utils.AppContentType
import utils.AppNavigationType
import utils.DeviceInfo
import utils.getAppLocalDimensions
import utils.getAppNavigationAndContentType

// Separate JsRootContent for web, due to using single navigation stack for maintaining browser history in decompose library
// becoz we cannot use separate WebHistoryController with nested StackNavigation, only one WebHistoryController can be used , thats why in case of web for we are using single StackNavigation
@Composable
fun JsRootContent(component: WebDesktopRootComponent, modifier: Modifier = Modifier) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    //TODO FIX, hindi locale is not working in web, but working in all other platforms android, iOS and desktop
    //StringDesc.localeType = StringDesc.LocaleType.Custom("hi") //custom forced locale

    //StringDesc.localeType = StringDesc.LocaleType.System //when localization depends on device settings

    lateinit var composeConfiguration: ComposeScreenConfiguration
    BoxWithConstraints(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        composeConfiguration = ComposeScreenConfiguration(maxWidth, maxHeight)

        val childStack = component.childStack

        CompositionLocalProvider(LocalComposeScreenConfiguration provides composeConfiguration) {
            AppLogger.d("screen_width=${LocalComposeScreenConfiguration.current.width}")
            val deviceInfo = DeviceInfo.calculateFromWidth(LocalComposeScreenConfiguration.current.width)

            val appNavigationAndContentType = getAppNavigationAndContentType(
                deviceInfo
            )

            val appNavigationType = appNavigationAndContentType.first
            val appContentType = appNavigationAndContentType.second

            AppLogger.d("appNavigationType=$appNavigationType")
            AppLogger.d("appContentType=$appContentType")

            val dimensions = getAppLocalDimensions(deviceInfo = deviceInfo)

            CompositionLocalProvider(LocalDimensions provides dimensions) {

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

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JsAppContent(
    appNavigationType: AppNavigationType,
    appContentType: AppContentType,
    childStack: Value<ChildStack<*, WebDesktopRootComponent.RootChild>>,
    modifier: Modifier,
    activeComponent: WebDesktopRootComponent.RootChild,
    component: WebDesktopRootComponent,
) {
    val activeComponent = component.childStack.active.instance
    //val showBottomBar = component.showBottomBar.subscribeAsState()
    AppLogger.d("activeComponent1=$activeComponent")
    JsNavContent(
        component,
        modifier = Modifier.fillMaxSize(),
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
    JsRootContent(PreviewWebDesktopRootComponent())
}

internal class PreviewWebDesktopRootComponent : WebDesktopRootComponent {
    override val childStack: Value<ChildStack<*, WebDesktopRootComponent.RootChild>> = MutableValue(
        ChildStack(
            configuration = Unit,
            instance = WebDesktopRootComponent.RootChild.HomeNavChild(component = PreviewHomeComponent()),
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