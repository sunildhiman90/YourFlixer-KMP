package home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import home.HomeComponent
import home.store.HomeStore
import kotlinx.coroutines.flow.Flow
import logger.AppLogger
import utils.AppNavigationType

@Composable
internal fun HomeContent(
    component: HomeComponent,
    modifier: Modifier = Modifier,
    appNavigationType: AppNavigationType,
    lazyListScrollBar: (@Composable (LazyListState, Modifier) -> Unit)? = null,
    lazyGridScrollBar: (@Composable (LazyGridState, Modifier) -> Unit)? = null,
    scrollBar: (@Composable (ScrollState, Modifier) -> Unit)? = null
) {

    val homeState = component.homeState.collectAsState(HomeStore.HomeState())

    // TODO, suppose api will be called here,lets suppose we go to some other screen, and come back,
    // then also it wil be called again due to recomposition, need to avoid that
    LaunchedEffect(Unit) {
        AppLogger.d("HomeContent_loadItems")
        component.loadItems()
    }

    val scrollState = rememberLazyListState()
    var modifier =   Modifier.padding(end = if (scrollBar != null) 16.dp else 0.dp)

    if(appNavigationType == AppNavigationType.BOTTOM_NAVIGATION) {
        HomeScreen(
            component::onFeedItemClicked,
            homeState,
            scrollState,
            modifier = modifier
        )
    } else {
        //For web and desktop, show  scrollbars
        Box(modifier = Modifier.fillMaxSize()) {

            HomeScreen(
                component::onFeedItemClicked,
                homeState,
                scrollState,
                modifier = modifier
            )

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.CenterEnd)
                    .fillMaxHeight(),
                visible = appNavigationType == AppNavigationType.NAVIGATION_RAIL || appNavigationType == AppNavigationType.PERMANENT_NAVIGATION_DRAWER
            ) {
                if (lazyListScrollBar != null) {
                    lazyListScrollBar(
                        scrollState,
                        Modifier.align(Alignment.CenterEnd)
                    )
                }
            }

        }
    }

}


//TODO, not rendering any preview
@Preview
@Composable
internal fun HomeScreenPreview() {
    HomeScreen(
        onFeedItemClick = { id -> },
        homeState = mutableStateOf(HomeStore.HomeState()),
        scrollState = rememberLazyListState()
    )
}

@Preview
@Composable
internal fun HomePreview() {
    HomeContent(
        component = PreviewHomeComponent(), appNavigationType = AppNavigationType.BOTTOM_NAVIGATION,
        lazyListScrollBar = null,
        lazyGridScrollBar = null,
        scrollBar = null,
    )
}

internal class PreviewHomeComponent : HomeComponent {

    override fun onFeedItemClicked(itemId: Long) {
        TODO("Not yet implemented")
    }

    override fun loadItems() {
        TODO("Not yet implemented")
    }

    override fun onBackClicked() {}
    override val homeState: Flow<HomeStore.HomeState>
        get() = TODO("Not yet implemented")


}
