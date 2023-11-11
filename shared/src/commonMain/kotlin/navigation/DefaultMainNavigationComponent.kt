package navigation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import core.navigation.RootDestination
import core.navigation.TopLevelDestination
import features.downloads.DownloadsComponent
import features.downloads.DownloadsComponentFactory
import features.homeroot.DefaultHomeRootComponent
import features.homeroot.HomeRootComponent
import features.homeroot.ui.PreviewHomeRootComponent
import features.profile.ProfileComponent
import features.profile.ProfileComponentFactory
import features.search.SearchComponent
import features.search.SearchComponentFactory
import features.stream.StreamVideoComponent
import utils.AppDispatchers
import utils.Consumer

open class DefaultMainNavigationComponent(
    componentContext: ComponentContext,
    private val dispatchers: AppDispatchers,
    private val homeRootComponentFactory: DefaultHomeRootComponent.Factory,
    private val searchComponentFactory: SearchComponentFactory,
    private val downloadsComponentFactory: DownloadsComponentFactory,
    private val profileComponentFactory: ProfileComponentFactory,
    private val navOutput: Consumer<MainNavigationComponent.Output>
) : MainNavigationComponent, ComponentContext by componentContext {


    private val navigation = StackNavigation<Config>()

    //TODO, try [childPages] for bottom tabs(if we want to support sliding gestures as well):
    // Child Pages is a navigation model for managing a list of components (pages) with one selected (active) component
    private val stack =
        childStack(
            source = navigation,
            initialStack = { getInitialStack() },
            childFactory = ::child,
            handleBackButton = true,
        )

    override val childStack: Value<ChildStack<*, MainNavigationComponent.RootChild>> = stack

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


    private fun onHomeRootComponentOutput(output: HomeRootComponent.Output) {
        when (output) {

            is HomeRootComponent.Output.OpenStreamVideo -> {
                //pass navOutput to RootComponent
                navOutput(MainNavigationComponent.Output.OpenStreamVideo(output.itemId))
            }
        }
    }

    private fun onSearchComponentOutput(output: SearchComponent.Output) {}

    private fun onDownloadsComponentOutput(output: DownloadsComponent.Output) {}

    private fun onProfileComponentOutput(output: ProfileComponent.Output) {}

    private fun onStreamVideoOutput(output: StreamVideoComponent.Output) {
        when (output) {
            StreamVideoComponent.Output.GoBack -> onBackPressed()
        }
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

    }


    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): MainNavigationComponent.RootChild =
        when (config) {
            is Config.Home -> MainNavigationComponent.RootChild.HomeNavChild(
                homeRootComponentFactory.create(
                    componentContext,
                    ::onHomeRootComponentOutput
                )
            )

            is Config.Search -> MainNavigationComponent.RootChild.SearchNavChild(
                searchComponentFactory.create(
                    componentContext,
                    ::onSearchComponentOutput
                )
            )


            is Config.Downloads -> MainNavigationComponent.RootChild.DownloadsNavChild(
                downloadsComponentFactory.create(
                    componentContext,
                    ::onDownloadsComponentOutput
                )
            )

            is Config.Profile -> MainNavigationComponent.RootChild.ProfileNavChild(
                profileComponentFactory.create(
                    componentContext,
                    userId = config.userId,
                    isBackEnabled = config.isBackEnabled,
                    goBack = ::onBackPressed,
                    output = ::onProfileComponentOutput
                )
            )
        }


    private companion object {

        private fun getInitialStack(): List<Config> = listOf(Config.Home)

    }

    class Factory(
        private val dispatchers: AppDispatchers,
        private val homeRootComponentFactory: DefaultHomeRootComponent.Factory,
        private val searchComponentFactory: SearchComponentFactory,
        private val downloadsComponentFactory: DownloadsComponentFactory,
        private val profileComponentFactory: ProfileComponentFactory,
    ) {
        fun create(
            componentContext: ComponentContext,
            navOutput: Consumer<MainNavigationComponent.Output>
        ) =
            DefaultMainNavigationComponent(
                componentContext,
                dispatchers,
                homeRootComponentFactory,
                searchComponentFactory,
                downloadsComponentFactory,
                profileComponentFactory,
                navOutput
            )
    }

}


//TODO
//@Preview
@Composable
internal fun NavContentPreview() {
    //NavContent(PreviewMainNavigationComponent())
}

internal class PreviewMainNavigationComponent : MainNavigationComponent {
    override val childStack: Value<ChildStack<*, MainNavigationComponent.RootChild>> = MutableValue(
        ChildStack(
            configuration = Unit,
            instance = MainNavigationComponent.RootChild.HomeNavChild(component = PreviewHomeRootComponent()),
        )
    )
    override val showBottomBar: MutableValue<Boolean>
        get() = TODO("Not yet implemented")
    override var lastSelectedTabDestination: RootDestination
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun onBackPressed() {
        TODO("Not yet implemented")
    }

    override fun onHomeTabClicked() {
        TODO("Not yet implemented")
    }

    override fun onSearchTabClicked() {
        TODO("Not yet implemented")
    }

    override fun onDownloadTabClicked() {
        TODO("Not yet implemented")
    }


    override fun onProfileTabClicked() {
        TODO("Not yet implemented")
    }

    override fun onAppNavigationRailItemClicked(selectedDestination: TopLevelDestination) {
        TODO("Not yet implemented")
    }

    override fun onBottomBarItemClicked(selectedDestination: TopLevelDestination) {
        TODO("Not yet implemented")
    }

}
