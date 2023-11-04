package home.store

import com.arkivanov.mvikotlin.core.store.Store
import home.data.FeedVideoItem


interface HomeStore : Store<HomeStore.HomeIntent, HomeStore.HomeState, Nothing> {

    sealed class HomeIntent {
        data object FetchItems : HomeIntent()
    }

    data class HomeState(
        val popularVideos: List<FeedVideoItem> = emptyList(),
        val nowPlayingVideos: List<FeedVideoItem> = emptyList(),
        val topRatedVideos: List<FeedVideoItem> = emptyList(),
        val isLoadingPopularVideos: Boolean = true,
        val isLoadingNowPlayingVideos: Boolean = true,
        val isLoadingTopRatedVideos: Boolean = true,
    )

}