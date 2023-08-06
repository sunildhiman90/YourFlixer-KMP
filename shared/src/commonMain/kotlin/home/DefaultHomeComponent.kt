package home

import com.arkivanov.decompose.ComponentContext
import utils.Consumer

internal class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val output: Consumer<HomeComponent.Output>
) : HomeComponent, ComponentContext by componentContext {


    override fun onFeedItemClicked(itemId: Long) {
        println(message = "onFeedItemClicked")
        output(HomeComponent.Output.OpenItemDetail(itemId))
    }

    override fun onBackClicked() {
        //navigation.pop()
    }

}
