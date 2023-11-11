package features.search.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import core.designsystem.component.SearchBar
import data.TestData
import getPlatformName
import kotlinx.coroutines.launch
import utils.AppPlatform
import utils.CustomImage
import utils.defaultIOSTopPadding
import utils.defaultIOSTopPaddingSearchScreen

@Composable
fun SearchScreen() {
    //in ios padding is required, because we have disabled the safe area from ios app,
    val originalModifier = Modifier
        .wrapContentHeight()
    val modifier =
        if (getPlatformName() == AppPlatform.IOS.name) originalModifier.padding(top = defaultIOSTopPaddingSearchScreen) else originalModifier

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(title = "Search")
    }
}
