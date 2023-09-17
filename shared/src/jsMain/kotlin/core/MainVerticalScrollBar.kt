package core

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainVerticalLazyListScrollBar(scrollState: LazyListState, modifier: Modifier) {
    //TODO, Getting this error here while click on scroll bar if we use official library scrollbar: An operation is not implemented: implement js setter for rawPosition
    // IN the original library, some code is commented for scrolling, so i copied 2 classes in jsMain module: [Scrollbar.js.jk] and [TapGestureDetector.kt]
    val adapter = utils.rememberScrollbarAdapter(scrollState)

//    Row(
//        modifier = modifier
//            .fillMaxHeight()
//            .background(color = MaterialTheme.colorScheme.surface)
//    ) {
//        Divider(
//            modifier = Modifier.width(1.dp)
//                .fillMaxHeight(),
//            color = MaterialTheme.colorScheme.outlineVariant
//        )
    utils.VerticalScrollbar(
        modifier = Modifier.padding(start = 3.dp, end = 3.dp),
        adapter = adapter,
        style = utils.LocalScrollbarStyle.current
    )
    //}

}


@Composable
fun MainVerticalScrollBar(scrollState: ScrollState, modifier: Modifier) {
    //TODO, Getting this error here while click on scroll bar if we use official library scrollbar: An operation is not implemented: implement js setter for rawPosition
    // IN the original library, some code is commented for scrolling, so i copied 2 classes in jsMain module: [Scrollbar.js.jk] and [TapGestureDetector.kt]
//    Row(
//        modifier = modifier
//            .fillMaxHeight()
//            .background(color = MaterialTheme.colorScheme.surface)
//    ) {
//        Divider(
//            modifier = Modifier.width(1.dp)
//                .fillMaxHeight(),
//            color = MaterialTheme.colorScheme.outlineVariant
//        )
    utils.VerticalScrollbar(
        modifier = Modifier.padding(start = 3.dp, end = 3.dp),
        adapter = utils.rememberScrollbarAdapter(scrollState),
        style = utils.LocalScrollbarStyle.current
    )

//    VerticalScrollbar(
//        modifier = Modifier.padding(start = 3.dp, end = 3.dp),
//        adapter = rememberScrollbarAdapter(scrollState),
//        style = LocalScrollbarStyle.current
//    )
    //}

}

//TODO, Scroll is not in alignment with mouse position in my custom implementation for LazyGridScrollbarAdapter
@Composable
fun MainVerticalLazyGridScrollBar(scrollState: LazyGridState, modifier: Modifier) {
    //TODO, Getting this error here while click on scroll bar if we use official library scrollbar: An operation is not implemented: implement js setter for rawPosition
    // IN the original library, some code is commented for scrolling, so i copied 2 classes in jsMain module: [Scrollbar.js.kt] and [TapGestureDetector.kt]
//    Row(
//        modifier = modifier
//            .fillMaxHeight()
//            .background(color = MaterialTheme.colorScheme.surface)
//    ) {
//        Divider(
//            modifier = Modifier.width(1.dp)
//                .fillMaxHeight(),
//            color = MaterialTheme.colorScheme.outlineVariant
//        )
    //TODO, depending on visibility of scrollbar, we need to hide divider as well
    utils.VerticalScrollbar(
        modifier = Modifier.padding(start = 3.dp, end = 3.dp),
        adapter = utils.rememberScrollbarAdapter(scrollState),
        style = utils.LocalScrollbarStyle.current
    )
    //}

}