package features.home.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import features.home.data.FeedVideoItem
import features.home.data.repo.HomeRepository
import features.home.store.HomeStore.HomeIntent
import features.home.store.HomeStore.HomeState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import logger.AppLogger

internal class HomeStoreProvider(
    private val storeFactory: StoreFactory,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val homeRepository: HomeRepository,
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
        data class PopularVideosLoaded(val items: List<FeedVideoItem>) : Msg()
        data class NowPlayingVideosLoaded(val items: List<FeedVideoItem>) : Msg()
        data class TopRatedVideosLoaded(val items: List<FeedVideoItem>) : Msg()
    }

    private sealed interface Action {
        class SetPopularVideosLoaded(val items: List<FeedVideoItem>): Action // <-- Use another Action
        class SetNowPlayingVideosLoaded(val items: List<FeedVideoItem>): Action
        class SetTopRatedVideosLoaded(val items: List<FeedVideoItem>): Action
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
                is Action.SetPopularVideosLoaded -> {
                    dispatch(Msg.PopularVideosLoaded(items = action.items))
                }
                is Action.SetNowPlayingVideosLoaded -> {
                    dispatch(Msg.NowPlayingVideosLoaded(items = action.items))
                }
                is Action.SetTopRatedVideosLoaded -> {
                    dispatch(Msg.TopRatedVideosLoaded(items = action.items))
                }
            }

        }

        override fun executeIntent(intent: HomeIntent, getState: () -> HomeState) {
            AppLogger.d("executeIntent=$intent")
            super.executeIntent(intent, getState)
            when(intent) {
                is HomeIntent.FetchItems -> {
                    fetchPopularVideos()
                    fetchNowPlayingVideos()
                    fetchTopRatedVideos()
                }
            }
        }

        private fun fetchPopularVideos() {
            AppLogger.d("fetchPopularVideos=")
            scope.launch {
                val items = withContext(dispatcher) {
                    val response = homeRepository.getPopularVideos()
                    //AppLogger.d("fetchNowPlayingVideos_Response=${response.results.take(5)}")
                    return@withContext response.results
                }
                dispatch(Msg.PopularVideosLoaded(items = items))
            }
        }

        private fun fetchNowPlayingVideos() {
            AppLogger.d("fetchNowPlayingVideos=")
            scope.launch {
                val items = withContext(dispatcher) {
                    val response = homeRepository.getNowPlayingVideos()
                    //AppLogger.d("fetchNowPlayingVideos_Response=${response.results.take(5)}")
                    return@withContext response.results
                }
                dispatch(Msg.NowPlayingVideosLoaded(items = items.filter { it.posterPath != null }))
            }
        }

        private fun fetchTopRatedVideos() {
            AppLogger.d("fetchTopRatedVideos=")
            scope.launch {
                val items = withContext(dispatcher) {
                    val response = homeRepository.getTopRatedVideos()
                    //AppLogger.d("fetchTopRatedVideos_Response=${response.results.take(5)}")
                    return@withContext response.results
                }
                dispatch(Msg.TopRatedVideosLoaded(items = items.filter { it.posterPath != null }))
            }
        }

    }

    //Please note that Reducers are not stateful and so we can use them as objects (singletons).
    private object ReducerImpl : Reducer<HomeState, Msg> {

        // After executor done fetching, it sends message to Reducer to change state
        override fun HomeState.reduce(msg: Msg): HomeState =
            when(msg) {
                is Msg.PopularVideosLoaded -> {
                    copy(popularVideos = msg.items, isLoadingPopularVideos = false)
                }
                is Msg.NowPlayingVideosLoaded -> {
                    copy(nowPlayingVideos = msg.items, isLoadingNowPlayingVideos = false)
                }
                is Msg.TopRatedVideosLoaded -> {
                    copy(topRatedVideos = msg.items, isLoadingTopRatedVideos = false)
                }
            }
    }




}