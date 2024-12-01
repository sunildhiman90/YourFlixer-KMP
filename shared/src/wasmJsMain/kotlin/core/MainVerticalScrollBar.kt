package core

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainVerticalLazyListScrollBar(scrollState: LazyListState, modifier: Modifier) {
    val adapter = rememberScrollbarAdapter(scrollState)
    VerticalScrollbar(
        modifier = Modifier.padding(start = 3.dp, end = 3.dp),
        adapter = adapter,
        style = LocalScrollbarStyle.current.copy(unhoverColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.50f))
    )

}


@Composable
fun MainVerticalScrollBar(scrollState: ScrollState, modifier: Modifier) {
    VerticalScrollbar(
        modifier = Modifier.padding(start = 3.dp, end = 3.dp),
        adapter = rememberScrollbarAdapter(scrollState),
        style = LocalScrollbarStyle.current
    )

}

@Composable
fun MainVerticalLazyGridScrollBar(scrollState: LazyGridState, modifier: Modifier) {
    VerticalScrollbar(
        modifier = Modifier.padding(start = 3.dp, end = 3.dp),
        adapter = rememberScrollbarAdapter(scrollState),
        style = LocalScrollbarStyle.current
    )
}