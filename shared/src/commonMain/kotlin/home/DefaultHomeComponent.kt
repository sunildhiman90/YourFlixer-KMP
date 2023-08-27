package home

import com.arkivanov.decompose.ComponentContext
import logger.AppLogger
import utils.AppDispatchers
import utils.Consumer

class DefaultHomeComponent(
    componentContext: ComponentContext,
    dispatchers: AppDispatchers,
    private val output: Consumer<HomeComponent.Output>
) : HomeComponent, ComponentContext by componentContext {


    override fun onFeedItemClicked(itemId: Long) {
        AppLogger.d(message = "onFeedItemClicked")
        output(HomeComponent.Output.OpenItemDetail(itemId))
    }

    override fun onBackClicked() {
        //navigation.pop()
    }

    class Factory(
        private val dispatchers: AppDispatchers,
    ) {
        fun create(
            componentContext: ComponentContext,
            output: Consumer<HomeComponent.Output>
        ) = DefaultHomeComponent(componentContext, dispatchers, output)
    }


}
