package home.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import data.FeedItem
import data.TestData
import home.store.HomeStore.HomeIntent
import home.store.HomeStore.HomeState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import logger.AppLogger

internal class HomeStoreProvider(
    private val storeFactory: StoreFactory,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    // Why factory and not just an instance of the Executor? Because of the time travel feature.
    // When debugging time travel events it creates separate instances of Executors when necessary and fakes their States.
    fun provide(): HomeStore = object : HomeStore,
        Store<HomeIntent, HomeState, Nothing> by storeFactory.create(
            name = "HomeStore",
            initialState = HomeStore.HomeState(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class ItemsLoaded(val items: List<FeedItem>) : Msg()
    }

    private sealed interface Action {
        class SetItems(val items: List<FeedItem>): Action // <-- Use another Action
    }


//    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
//        override fun invoke() = fetchItems()
//
//        fun fetchItems() {
//            //TODO, this function is repeating in BootstrapperImpl and ExecutorImpl
//            scope.launch {
//                val items = withContext(dispatcher) {
//                    //TODO, fetch from network using repo
//                    return@withContext TestData.feedList1
//                }
//                dispatch(Action.SetItems(items = items))
//            }
//        }
//    }

    //Please note that Executors are stateful and so can not be objects (singletons).
    private inner class ExecutorImpl : CoroutineExecutor<HomeIntent, Action, HomeState, Msg, Nothing>() {

        //Execute action from bootstrapper(after loading data) and send message to reducer for udpating state
        override fun executeAction(action: Action, getState: () -> HomeState) {
            super.executeAction(action, getState)
            when(action) {
                is Action.SetItems -> {
                    dispatch(Msg.ItemsLoaded(items = action.items))
                }
            }

        }

        override fun executeIntent(intent: HomeIntent, getState: () -> HomeState) {
            AppLogger.d("executeIntent=$intent")
            super.executeIntent(intent, getState)
            when(intent) {
                is HomeIntent.FetchItems -> fetchItems()
            }
        }

        private fun fetchItems() {
            AppLogger.d("fetchItems=")
            scope.launch {
                val items = withContext(dispatcher) {
                    //TODO, fetch from network using repo
                    return@withContext TestData.feedList1
                }
                dispatch(Msg.ItemsLoaded(items = items))
            }
        }

    }

    //Please note that Reducers are not stateful and so we can use them as objects (singletons).
    private object ReducerImpl : Reducer<HomeState, Msg> {

        // After executor done fetching, it sends message to Reducer to change state
        override fun HomeState.reduce(msg: Msg): HomeState =
            when(msg) {
                is Msg.ItemsLoaded -> {
                    copy(items = msg.items, isLoading = true)
                }
            }
    }




}