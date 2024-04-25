package features.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.states
import features.home.data.repo.HomeRepository
import features.home.store.HomeStore
import features.home.store.HomeStoreProvider
import kotlinx.coroutines.flow.Flow
import logger.AppLogger
import utils.AppDispatchers
import utils.Consumer

class DefaultHomeComponent(
    componentContext: ComponentContext,
    dispatchers: AppDispatchers,
    private val storeFactory: StoreFactory,
    private val homeRepository: HomeRepository,
    private val output: Consumer<HomeComponent.Output>
) : HomeComponent, ComponentContext by componentContext {

    //If we dont use MVI Store, then WE can use this way instead of using it from LaunchedEffect in which case it will re call api everytime coming to home screen
//    init {
//        loadItems()
//    }

    //similar to viewModel
    private val store =
        instanceKeeper.getStore {
            HomeStoreProvider(
                storeFactory = storeFactory,
                dispatcher = dispatchers.Default,
                homeRepository = homeRepository
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
    private val homeRepository: HomeRepository
) {
    fun create(
        componentContext: ComponentContext,
        output: Consumer<HomeComponent.Output>
    ) = DefaultHomeComponent(componentContext, dispatchers, storeFactory, homeRepository, output)
}