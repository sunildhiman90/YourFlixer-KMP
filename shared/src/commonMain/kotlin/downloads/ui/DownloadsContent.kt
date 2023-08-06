package downloads.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import downloads.DownloadsComponent

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
