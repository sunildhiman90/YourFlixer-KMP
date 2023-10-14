package home.store

import com.arkivanov.mvikotlin.core.store.Store
import data.FeedItem


interface HomeStore : Store<HomeStore.HomeIntent, HomeStore.HomeState, Nothing> {

    sealed class HomeIntent {
        data object FetchItems : HomeIntent()
    }

    data class HomeState(
        val items: List<FeedItem> = emptyList(),
        val isLoading: Boolean = true
    )

}