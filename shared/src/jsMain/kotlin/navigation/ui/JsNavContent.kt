package navigation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import core.MainVerticalLazyGridScrollBar
import core.MainVerticalLazyListScrollBar
import core.MainVerticalScrollBar
import core.designsystem.component.CommonAppBottomBar
import core.navigation.AppNavigationRail
import core.navigation.ModalNavigationDrawerContent
import core.navigation.PermanentNavigationDrawerContent
import core.navigation.TopLevelDestination
import downloads.ui.DownloadsContent
import home.ui.HomeContent
import itemdetail.ui.ItemDetailContent
import kotlinx.coroutines.launch
import profile.ui.ProfileContent
import root.RootComponent
import root.WebDesktopRootComponent
import search.ui.SearchContent
import stream.ui.StreamVideoContent
import utils.AppContentType
import utils.AppNavigationContentPosition
import utils.AppNavigationType

//Separate NavContent for web, due to using single navigation stack for maintaining browser history
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun JsNavContent(
    component: WebDesktopRootComponent,
    modifier: Modifier,
    appNavigationType: AppNavigationType,
    appContentType: AppContentType,
    lazyListScrollBar: (@Composable (LazyListState, Modifier) -> Unit)? = null,
    lazyGridScrollBar: (@Composable (LazyGridState, Modifier) -> Unit)? = null,
    scrollBar: (@Composable (ScrollState, Modifier) -> Unit)? = null
) {

    WebDesktopNavContent(
        component,
        modifier,
        appNavigationType,
        appContentType,
        scrollBar = scrollBar,
        lazyListScrollBar = lazyListScrollBar,
        lazyGridScrollBar = lazyGridScrollBar
    )

}