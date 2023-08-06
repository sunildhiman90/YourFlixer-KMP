package stream.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import stream.StreamVideoComponent

@Composable
internal fun StreamVideoContent(
    component: StreamVideoComponent,
    modifier: Modifier = Modifier,
) {
    StreamVideoScreen(component::onBackClicked)
}

//@Preview
@Composable
internal fun StreamVideoComponentPreview() {
    StreamVideoContent(component = PreviewStreamVideoComponent())
}

internal class PreviewStreamVideoComponent : StreamVideoComponent {

    override fun onBackClicked() {}
}
