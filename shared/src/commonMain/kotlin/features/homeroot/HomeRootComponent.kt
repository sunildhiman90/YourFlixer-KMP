package features.homeroot

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import core.component.Component
import features.home.HomeComponent
import features.itemdetail.ItemDetailComponent

interface HomeRootComponent : Component {

    val childStack: Value<ChildStack<*, HomeChild>>

    fun onBackClicked()

    fun onFeedItemClicked(itemId: Long)


    sealed class HomeChild {

        data class HomeMainChild(
            val component: HomeComponent,
        ) : HomeChild()

        data class HomeItemDetailChild(
            val component: ItemDetailComponent,
        ) : HomeChild()
    }

    sealed class Output {

        data class OpenStreamVideo(val itemId: Long) : Output()
    }
}