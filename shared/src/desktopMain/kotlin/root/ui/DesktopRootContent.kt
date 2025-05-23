@file:Suppress("OPTIONAL_DECLARATION_USAGE_IN_NON_COMMON_SOURCE") // Workaround for KTIJ-22326

package root.ui


import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
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
import navigation.ui.DesktopNavContent
import root.WebDesktopRootComponent
import utils.AppContentType
import utils.AppNavigationType
import utils.DeviceInfo
import utils.getAppLocalDimensions
import utils.getAppNavigationAndContentType

@Composable
fun DesktopRootContent(component: WebDesktopRootComponent, modifier: Modifier = Modifier) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance


    //StringDesc.localeType = StringDesc.LocaleType.Custom("hi") //custom forced locale

    //StringDesc.localeType = StringDesc.LocaleType.System //when localization depends on device settings


    lateinit var composeConfiguration: ComposeScreenConfiguration
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        composeConfiguration = ComposeScreenConfiguration(maxWidth, maxHeight)


        val childStack = component.childStack
        
        CompositionLocalProvider(LocalComposeScreenConfiguration provides composeConfiguration) {
            val deviceInfo = DeviceInfo.calculateFromWidth(LocalComposeScreenConfiguration.current.width)

            AppLogger.d("screen_width=${LocalComposeScreenConfiguration.current.width}")
            val appNavigationAndContentType = getAppNavigationAndContentType(
                DeviceInfo.calculateFromWidth(LocalComposeScreenConfiguration.current.width)
            )

            val appNavigationType = appNavigationAndContentType.first
            val appContentType = appNavigationAndContentType.second

            AppLogger.d("appNavigationType=$appNavigationType")
            AppLogger.d("appContentType=$appContentType")

            val dimensions = getAppLocalDimensions(deviceInfo = deviceInfo)

            CompositionLocalProvider(LocalDimensions provides dimensions) {

                DesktopAppContent(
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
private fun DesktopAppContent(
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
    DesktopNavContent(
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
internal fun DesktopRootContentPreview() {
    DesktopRootContent(PreviewDesktopRootComponent())
}

internal class PreviewDesktopRootComponent : WebDesktopRootComponent {
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