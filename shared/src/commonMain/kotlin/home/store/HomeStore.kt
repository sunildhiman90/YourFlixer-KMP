package home.store

import com.arkivanov.mvikotlin.core.store.Store
import home.data.FeedVideoItem


interface HomeStore : Store<HomeStore.HomeIntent, HomeStore.HomeState, Nothing> {

    sealed class HomeIntent {
        data object FetchItems : HomeIntent()
    }

    data class HomeState(
        val items: List<FeedVideoItem> = emptyList(),
        val isLoading: Boolean = true
    )

}