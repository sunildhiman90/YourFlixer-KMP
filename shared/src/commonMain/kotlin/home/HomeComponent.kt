package home

import core.component.Component

interface HomeComponent : Component {


    fun onFeedItemClicked(itemId: Long)

    fun onBackClicked()


    sealed class Output {
        data class OpenStreamVideo(val itemId: Long) : Output()

        data class OpenItemDetail(val itemId: Long) : Output()
    }

}