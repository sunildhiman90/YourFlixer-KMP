package features.home.ui

import AppConstants
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yourflixer.common.MR
import core.LocalDimensions
import core.designsystem.component.CommonTopAppBar
import dev.icerock.moko.resources.compose.stringResource
import getPlatformName
import features.home.data.FeedVideoItem
import features.home.store.HomeStore
import kotlinx.coroutines.launch
import utils.AppPlatform
import utils.CustomImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onFeedItemClick: (Long) -> Unit,
    homeState: State<HomeStore.HomeState>,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    val appName = stringResource(MR.strings.app_name)
    Scaffold(
        topBar = {
            Column {
                if (getPlatformName() == AppPlatform.ANDROID.name || getPlatformName() == AppPlatform.IOS.name)
                    CommonTopAppBar(
                        titleComposable = {
                            Text(
                                text = appName,
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

        //val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyColumn(
            state = scrollState,
            modifier = modifier
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
            verticalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(bottom = LocalDimensions.current.horizontalPadding * 6)
        ) {

            //Most Popular videos
            item {
                HomeFeedSection {
                    Column(
                        modifier = Modifier.wrapContentSize(),
                    ) {
                        Text(
                            stringResource(MR.strings.most_popular),
                            modifier = Modifier.padding(
                                horizontal = LocalDimensions.current.mediumPadding,
                                vertical = LocalDimensions.current.mediumPadding
                            ),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        if (homeState.value.isLoadingPopularVideos) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(Alignment.Center)
                            )
                        } else {
                            HomeFeedRow(
                                feedList = homeState.value.popularVideos,
                                onFeedItemClick = onFeedItemClick,
                            )
                        }
                    }

                }
            }

            // Now Playing Videos
            item {
                HomeFeedSection {
                    Column {
                        Text(
                            stringResource(MR.strings.now_playing),
                            modifier = Modifier.padding(
                                horizontal = LocalDimensions.current.mediumPadding,
                                vertical = LocalDimensions.current.mediumPadding
                            ),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        if (homeState.value.isLoadingNowPlayingVideos) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(Alignment.Center)
                            )
                        } else {
                            HomeFeedRow(
                                feedList = homeState.value.nowPlayingVideos,
                                onFeedItemClick = onFeedItemClick,
                            )
                        }

                    }

                }
            }

            // Top Rated Videos
            item {
                HomeFeedSection {
                    Column {
                        Text(
                            stringResource(MR.strings.top_rated),
                            modifier = Modifier.padding(
                                horizontal = LocalDimensions.current.mediumPadding,
                                vertical = LocalDimensions.current.mediumPadding
                            ),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        if (homeState.value.isLoadingTopRatedVideos) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(Alignment.Center)
                            )
                        } else {
                            HomeFeedRow(
                                feedList = homeState.value.topRatedVideos,
                                onFeedItemClick = onFeedItemClick,
                            )
                        }

                    }

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
    feedList: List<FeedVideoItem>,
    onFeedItemClick: (Long) -> Unit,
    contentPadding: PaddingValues = PaddingValues(horizontal = LocalDimensions.current.mediumPadding),
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
        horizontalArrangement = Arrangement.spacedBy(LocalDimensions.current.smallPadding),
        contentPadding = contentPadding // If we apply simple modifier padding here, when scrolling, the first and last visible item are cut off on both sides of the screen,To maintain the same padding, but still scroll your content within the bounds of your parent list without clipping it, all lists provide a parameter called contentPadding, so we will use contentPadding
    ) {
        items(
            feedList,
            key = { item: FeedVideoItem ->
                item.id
            }
        ) { feedItem ->
            //AppLogger.d("img_path=${AppConstants.IMAGES_BASE_URL + AppConstants.IMAGES_SIZE_SUFFIX + feedItem.posterPath}")
            HomeFeedItem(
                item = feedItem,
                url = AppConstants.IMAGES_BASE_URL + AppConstants.IMAGES_SIZE_SUFFIX + feedItem.posterPath,
                title = feedItem.originalTitle,
                onFeedItemClick = onFeedItemClick
            )
        }
    }
}

@Composable
fun HomeFeedItem(
    item: FeedVideoItem,
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
            modifier = Modifier
                .height(LocalDimensions.current.homeFeedImageHeight)
                .width(LocalDimensions.current.homeFeedImageWidth)
                .clip(RoundedCornerShape(LocalDimensions.current.defaultShapeCornerRadius))
                .border(
                    border = BorderStroke(
                        width = LocalDimensions.current.defaultBorderWidth,
                        color = MaterialTheme.colorScheme.outline
                    ),
                    shape = RoundedCornerShape(LocalDimensions.current.defaultShapeCornerRadius)
                ),
            url = url,
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
        /*        Text(
                    text = title,
                    maxLines = 2,
                    style = if (getPlatformName() == AppPlatform.WEB.name) MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                    ) else MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .paddingFromBaseline(
                            top = LocalDimensions.current.horizontalPadding + LocalDimensions.current.halfPadding,
                            bottom = LocalDimensions.current.halfPadding
                        ),
                )*/
    }
}
