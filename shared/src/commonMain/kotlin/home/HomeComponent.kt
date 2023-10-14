package home

import core.component.Component
import home.store.HomeStore
import kotlinx.coroutines.flow.Flow

interface HomeComponent : Component {


    fun onFeedItemClicked(itemId: Long)

    fun loadItems()

    fun onBackClicked()


    val homeState: Flow<HomeStore.HomeState>


    sealed class Output {
        data class OpenStreamVideo(val itemId: Long) : Output()

        data class OpenItemDetail(val itemId: Long) : Output()
    }

}