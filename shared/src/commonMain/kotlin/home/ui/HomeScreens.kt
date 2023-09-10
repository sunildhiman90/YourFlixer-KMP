package home.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yourflixer.common.Res
import com.yourflixer.common.strings.ResStrings
import core.designsystem.component.CommonTopAppBar
import data.FeedItem
import data.TestData
import getPlatformName
import io.github.skeptick.libres.compose.painterResource
import kotlinx.coroutines.launch
import utils.AppPlatform
import utils.CustomImage
import utils.Strings

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    onFeedItemClick: (Long) -> Unit,
) {

    Scaffold(
        topBar = {
            Column {
                CommonTopAppBar(
                    titleComposable = {
                        Text(
                            text = Res.string.app_name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    navigationIcon = null,
                    actionIconContentDescription = "",
                    windowInsetsPadding = WindowInsets(0.dp),
                    showActions = false
                )
            }
        }
    ) { innerPadding ->

        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                //this is required for scrolling support in desktop and web
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            scrollState.scrollBy(-delta)
                        }
                    },
                ).onKeyEvent {
                    when {
                        (it.key == Key.DirectionDown && it.type == KeyEventType.KeyDown) -> {

                            coroutineScope.launch {
                                scrollState.scrollBy(10f)
                            }
                            true
                        }

                        (it.type == KeyEventType.KeyUp) -> {
                            true
                        }

                        else -> false
                    }
                },
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {

//            item {
//                Image(painter = Res.image.compose_multiplatform.painterResource(), contentScale = ContentScale.Fit, contentDescription = "")
//            }

            item {
                HomeFeedSection {
                    HomeFeedRow(
                        feedList = TestData.feedList1,
                        modifier = Modifier.padding(top = 16.dp),
                        onFeedItemClick = onFeedItemClick,
                    )
                }
            }
            
        }

    }
}

// Recommended approach -> Slot based api with content: @Composable () method as last parameter
@Composable
fun HomeFeedSection(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    content()
}

@Composable
fun HomeFeedRow(
    modifier: Modifier = Modifier,
    feedList: List<FeedItem>,
    onFeedItemClick: (Long) -> Unit,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
) {

    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyRow(
        state = scrollState,
        modifier = modifier.draggable(
            orientation = Orientation.Horizontal,
            state = rememberDraggableState { delta ->
                coroutineScope.launch {
                    scrollState.scrollBy(-delta)
                }
            },
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding // If we apply simple modifier padding here, when scrolling, the first and last visible item are cut off on both sides of the screen,To maintain the same padding, but still scroll your content within the bounds of your parent list without clipping it, all lists provide a parameter called contentPadding, so we will use contentPadding
    ) {
        items(
            feedList,
            key = { item: FeedItem ->
                item.id
            }
        ) { feedItem ->
            HomeFeedItem(
                item = feedItem,
                url = feedItem.url,
                title = feedItem.title,
                onFeedItemClick = onFeedItemClick
            )
        }
    }
}

@Composable
fun HomeFeedItem(
    item: FeedItem,
    onFeedItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    url: String,
    title: String,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null //to remove shadow on hover or click
        ) {
            onFeedItemClick(item.id.toLong())
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomImage(
            modifier = Modifier.height(100.dp).width(70.dp).border(
                border = BorderStroke(
                    width = 1.dp, color = MaterialTheme.colorScheme.outline
                )
            ),
            url = url,
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
        Text(
            text = title,
            style = if (getPlatformName() == AppPlatform.WEB.name) MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Normal,
            ) else MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .paddingFromBaseline(
                    top = 24.dp,
                    bottom = 8.dp
                ),
        )
    }
}

@Preview()
@Composable
fun HomeFeedRowPreview() {
    HomeFeedRow(
        modifier = Modifier.padding(top = 16.dp),
        feedList = TestData.feedList1,
        onFeedItemClick = {}
    )
}
