package home.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import home.HomeComponent
import home.store.HomeStore
import kotlinx.coroutines.flow.Flow
import logger.AppLogger

@Composable
internal fun HomeContent(
    component: HomeComponent,
    modifier: Modifier = Modifier,
) {

    val homeState = component.homeState.collectAsState(HomeStore.HomeState())

    // TODO, suppose api will be called here,lets suppose we go to some other screen, and come back,
    // then also it wil be called again due to recomposition, How to avoid that
    LaunchedEffect(Unit) {
        AppLogger.d("HomeContent_loadItems")
        component.loadItems()
    }

    HomeScreen(
        component::onFeedItemClicked,
    )
}


//TODO, not rendering any preview
@Preview
@Composable
internal fun HomeScreenPreview() {
    HomeScreen(
        onFeedItemClick = { id -> },
    )
}

@Preview
@Composable
internal fun HomePreview() {
    HomeContent(component = PreviewHomeComponent())
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
