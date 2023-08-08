package home.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import home.HomeComponent

@Composable
internal fun HomeContent(
    component: HomeComponent,
    modifier: Modifier = Modifier,
) {
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

    override fun onBackClicked() {}


}
