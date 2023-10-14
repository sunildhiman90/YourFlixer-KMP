package home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.states
import home.store.HomeStore
import home.store.HomeStoreProvider
import kotlinx.coroutines.flow.Flow
import logger.AppLogger
import utils.AppDispatchers
import utils.Consumer

class DefaultHomeComponent(
    componentContext: ComponentContext,
    dispatchers: AppDispatchers,
    private val storeFactory: StoreFactory,
    private val output: Consumer<HomeComponent.Output>
) : HomeComponent, ComponentContext by componentContext {

    //similar to viewModel
    private val store =
        instanceKeeper.getStore {
            HomeStoreProvider(
                storeFactory = storeFactory,
                dispatcher = dispatchers.Default
            ).provide()
        }

    override val homeState: Flow<HomeStore.HomeState> = store.states

    override fun loadItems() {
        // Send intent to Store and Store Accepts Intents and passes them to the Executor.
        store.accept(HomeStore.HomeIntent.FetchItems)
    }

    override fun onFeedItemClicked(itemId: Long) {
        AppLogger.d(message = "onFeedItemClicked")
        output(HomeComponent.Output.OpenItemDetail(itemId))
    }

    override fun onBackClicked() {
        //navigation.pop()
    }

}

class HomeComponentFactory(
    private val dispatchers: AppDispatchers,
    private val storeFactory: StoreFactory,
) {
    fun create(
        componentContext: ComponentContext,
        output: Consumer<HomeComponent.Output>
    ) = DefaultHomeComponent(componentContext, dispatchers, storeFactory, output)
}