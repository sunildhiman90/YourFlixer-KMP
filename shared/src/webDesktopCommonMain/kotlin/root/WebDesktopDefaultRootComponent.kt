package root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import core.component.DeepLink
import core.navigation.RootDestination
import core.navigation.TopLevelDestination
import downloads.DownloadsComponent
import downloads.DownloadsComponentFactory
import home.HomeComponent
import home.HomeComponentFactory
import itemdetail.ItemDetailComponent
import itemdetail.ItemDetailComponentFactory
import logger.AppLogger
import profile.ProfileComponent
import profile.ProfileComponentFactory
import search.SearchComponent
import search.SearchComponentFactory
import stream.StreamVideoComponent
import stream.StreamVideoComponentFactory
import utils.AppDispatchers

@OptIn(ExperimentalDecomposeApi::class)
open class WebDesktopDefaultRootComponent(
    componentContext: ComponentContext,
    deepLink: DeepLink,
    webHistoryController: WebHistoryController?,
    private val dispatchers: AppDispatchers,
    private val homeComponentFactory: HomeComponentFactory,
    private val searchComponentFactory: SearchComponentFactory,
    private val downloadsComponentFactory: DownloadsComponentFactory,
    private val profileComponentFactory: ProfileComponentFactory,
    private val streamVideoComponentFactory: StreamVideoComponentFactory,
    private val itemDetailComponentFactory: ItemDetailComponentFactory,
) : WebDesktopRootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val stack =
        childStack(
            source = navigation,
            initialStack = { getInitialStack(deepLink) },
            childFactory = ::child,
            handleBackButton = true,
        )

    override val childStack: Value<ChildStack<*, WebDesktopRootComponent.RootChild>> = stack

    override val showBottomBar: MutableValue<Boolean>
        get() = MutableValue(true)

    override var lastSelectedTabDestination: RootDestination = TopLevelDestination.HOME

    override fun onHomeTabClicked() {
        navigation.bringToFront(Config.Home)
    }

    override fun onSearchTabClicked() {
        navigation.bringToFront(Config.Search)
    }

    override fun onDownloadTabClicked() {
        navigation.bringToFront(Config.Downloads)
    }

    override fun onProfileTabClicked() {
        navigation.bringToFront(Config.Profile(isBackEnabled = false))
    }

    override fun onAppNavigationRailItemClicked(selectedDestination: TopLevelDestination) {
        onNavItemClicked(selectedDestination)
    }

    override fun onBottomBarItemClicked(selectedDestination: TopLevelDestination) {
        onNavItemClicked(selectedDestination)
    }

    private fun onNavItemClicked(selectedDestination: TopLevelDestination) {
        when (selectedDestination) {
            TopLevelDestination.HOME -> onHomeTabClicked()
            TopLevelDestination.SEARCH -> onSearchTabClicked()
            TopLevelDestination.DOWNLOADS -> onDownloadTabClicked()
            TopLevelDestination.PROFILE -> onProfileTabClicked()
        }
    }

    override fun onBackPressed() {
        if (childStack.value.items.size > 1) {
            navigation.pop()

            //this will be needed in case if we are using nested navigation not with separate child navigation
            // but with this root stack navigation by passing it down

//            if (childStack.value.items.size == 1) {
//                showHideBottomBar(true)
//            }
        }
    }


    /**
     *  //if we have any of other full screen or non tab destinations, we need to pop them,
     *  otherwise it will throw error when we will push them again, SO that case was not working properly, so i applied
     *  bringToFront method here...
     */
    private fun onHomeComponentOutput(output: HomeComponent.Output) {
        when (output) {
            is HomeComponent.Output.OpenStreamVideo -> {
                navigation.bringToFront(
                    Config.StreamVideo(output.itemId)
                )
            }

            is HomeComponent.Output.OpenItemDetail -> {
                //instead of push, use bringToFront here,
                // otherwise we will get error of pushing same configuration again (configuration must be unique),
                // in case if we click on feed profile ,and then click on some another tab, and then from home,again click on profile
                navigation.bringToFront(
                    Config.ItemDetail(output.itemId, isBackEnabled = true)
                )
            }
        }
    }

    private fun onStreamVideoOutput(output: StreamVideoComponent.Output) {
        when (output) {
            StreamVideoComponent.Output.GoBack -> onBackPressed()
        }
    }

    private fun onSearchComponentOutput(output: SearchComponent.Output) {}

    private fun onDownloadsComponentOutput(output: DownloadsComponent.Output) {}

    private fun onProfileComponentOutput(output: ProfileComponent.Output) {}

    private fun onItemDetailComponentOutput(output: ItemDetailComponent.Output) {}

    init {
        webHistoryController?.attach(
            navigator = navigation,
            stack = stack,
            getPath = ::getPathForConfig,
            getConfiguration = ::getConfigForPath,
        )
    }

    private sealed interface Config : Parcelable {
        @Parcelize
        object Home : Config {
            /**
             * Only required for state preservation on JVM/desktop via StateKeeper, as it uses Serializable.
             * Temporary workaround for https://youtrack.jetbrains.com/issue/KT-40218.
             */
            @Suppress("unused")
            private fun readResolve(): Any = Home
        }

        @Parcelize
        object Search : Config {
            /**
             * Only required for state preservation on JVM/desktop via StateKeeper, as it uses Serializable.
             * Temporary workaround for https://youtrack.jetbrains.com/issue/KT-40218.
             */
            @Suppress("unused")
            private fun readResolve(): Any = Search
        }

        @Parcelize
        object Downloads : Config {
            /**
             * Only required for state preservation on JVM/desktop via StateKeeper, as it uses Serializable.
             * Temporary workaround for https://youtrack.jetbrains.com/issue/KT-40218.
             */
            @Suppress("unused")
            private fun readResolve(): Any = Downloads
        }

        //logged in user profile
        @Parcelize
        data class Profile(
            val userId: Long? = null,
            val isBackEnabled: Boolean = false,
        ) : Config {
            /**
             * Only required for state preservation on JVM/desktop via StateKeeper, as it uses Serializable.
             * Temporary workaround for https://youtrack.jetbrains.com/issue/KT-40218.
             */
            @Suppress("unused")
            private fun readResolve(): Any = Profile(userId, isBackEnabled)
        }

        @Parcelize
        data class ItemDetail(
            val itemId: Long? = null,
            val isBackEnabled: Boolean = false,
        ) : Config {
            /**
             * Only required for state preservation on JVM/desktop via StateKeeper, as it uses Serializable.
             * Temporary workaround for https://youtrack.jetbrains.com/issue/KT-40218.
             */
            @Suppress("unused")
            private fun readResolve(): Any = ItemDetail(itemId, isBackEnabled)
        }

        @Parcelize
        data class StreamVideo(val itemId: Long) : Config {
            /**
             * Only required for state preservation on JVM/desktop via StateKeeper, as it uses Serializable.
             * Temporary workaround for https://youtrack.jetbrains.com/issue/KT-40218.
             */
            @Suppress("unused")
            private fun readResolve(): Any = StreamVideo(itemId)
        }
    }

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): WebDesktopRootComponent.RootChild {
        AppLogger.d("searchComponentFactory=${searchComponentFactory::create}")
        return when (config) {
            is Config.Home -> WebDesktopRootComponent.RootChild.HomeNavChild(
                homeComponentFactory.create(
                    componentContext = componentContext,
                    output = ::onHomeComponentOutput
                )
            )

            is Config.Search -> WebDesktopRootComponent.RootChild.SearchNavChild(
                searchComponentFactory.create(
                    componentContext,
                    ::onSearchComponentOutput
                )
            )


            is Config.Downloads -> WebDesktopRootComponent.RootChild.DownloadsNavChild(
                downloadsComponentFactory.create(
                    componentContext,
                    ::onDownloadsComponentOutput
                )
            )

            is Config.Profile -> WebDesktopRootComponent.RootChild.ProfileNavChild(
                profileComponentFactory.create(
                    componentContext,
                    userId = config.userId,
                    isBackEnabled = config.isBackEnabled,
                    goBack = ::onBackPressed,
                    output = ::onProfileComponentOutput
                )
            )

            is Config.StreamVideo -> WebDesktopRootComponent.RootChild.StreamVideoChild(
                streamVideoComponentFactory.create(
                    componentContext,
                    ::onStreamVideoOutput,
                )
            )

            is Config.ItemDetail -> WebDesktopRootComponent.RootChild.ItemDetailChild(
                itemDetailComponentFactory.create(
                    componentContext,
                    itemId = config.itemId,
                    goBack = ::onBackPressed,
                    output = ::onItemDetailComponentOutput
                )
            )
        }
    }


    private companion object {
        private const val WEB_PATH_HOME = "home"
        private const val WEB_PATH_SEARCH = "search"
        private const val WEB_PATH_DOWNLOADS = "downloads"
        private const val WEB_PATH_PROFILE = "profile"
        private const val WEB_PATH_STREAM_VIDEO = "watch" //TODO, how to pass url parameters here
        private const val WEB_PATH_ITEM_DETAIL = "item"

        private fun getInitialStack(deepLink: DeepLink): List<Config> =
            when (deepLink) {
                is DeepLink.None -> listOf(Config.Home)
                is DeepLink.Web -> listOf(getConfigForPath(deepLink.path))
            }

        private fun getPathForConfig(config: Config): String =
            when (config) {
                is Config.Home -> "/$WEB_PATH_HOME"
                is Config.Search -> "/$WEB_PATH_SEARCH"
                is Config.Downloads -> "/$WEB_PATH_DOWNLOADS"
                is Config.Profile -> {
                    if (config.userId != null) {
                        "/$WEB_PATH_PROFILE/${config.userId}"
                    } else {
                        "/$WEB_PATH_PROFILE"
                    }
                }

                is Config.StreamVideo -> "/$WEB_PATH_STREAM_VIDEO/${config.itemId}"
                is Config.ItemDetail -> "/$WEB_PATH_ITEM_DETAIL/${config.itemId}"
            }

        private fun getConfigForPath(path: String): Config {
            val finalPath = path.removePrefix("/")
            return when {
                finalPath == WEB_PATH_HOME -> Config.Home
                finalPath == WEB_PATH_SEARCH -> Config.Search
                finalPath == WEB_PATH_DOWNLOADS -> Config.Downloads
                finalPath.contains(WEB_PATH_PROFILE) -> {
                    val urlData = finalPath.split("/")
                    val userId = if (urlData.size > 1) {
                        urlData[1]
                    } else {
                        null
                    }
                    Config.Profile(userId = userId?.toLong())
                }

                finalPath.contains(WEB_PATH_STREAM_VIDEO) -> {
                    val urlData = finalPath.split("/")
                    val itemId = urlData[1]
                    Config.StreamVideo(itemId.toLong())
                }

                finalPath.contains(WEB_PATH_ITEM_DETAIL) -> {
                    val urlData = finalPath.split("/")
                    val id = urlData[1]
                    Config.ItemDetail(id.toLong())
                }

                else -> Config.Home
            }
        }
    }

}