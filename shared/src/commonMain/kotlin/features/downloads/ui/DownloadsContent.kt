package features.downloads.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import features.downloads.DownloadsComponent

@Composable
internal fun DownloadsContent(component: DownloadsComponent, modifier: Modifier = Modifier) {
    DownloadsScreen()
}

//@Preview
@Composable
internal fun DownloadsPreview() {
    DownloadsContent(component = PreviewDownloadsComponent())
}

internal class PreviewDownloadsComponent : DownloadsComponent {
    
    override fun onBackClicked() {}
}
