package itemdetail.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import itemdetail.ItemDetailComponent

@Composable
internal fun ItemDetailContent(component: ItemDetailComponent, modifier: Modifier = Modifier) {

    val uriHandler = LocalUriHandler.current

    ItemDetailScreen(
        onLinkClick = {
            uriHandler.openUri(it)
        },
        onBackPressed = {
            component.onBackClicked()
        },
    )
}

//@Preview
@Composable
internal fun ItemDetailPreview() {
    ItemDetailContent(component = PreviewItemDetailComponent())
}

internal class PreviewItemDetailComponent() : ItemDetailComponent {

    override fun onBackClicked() {}
}
