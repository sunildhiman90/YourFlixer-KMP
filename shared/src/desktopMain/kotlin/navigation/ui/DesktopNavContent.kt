package navigation.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import root.WebDesktopRootComponent
import utils.AppContentType
import utils.AppNavigationType

//Separate NavContent for web, due to using single navigation stack for maintaining browser history
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun DesktopNavContent(
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