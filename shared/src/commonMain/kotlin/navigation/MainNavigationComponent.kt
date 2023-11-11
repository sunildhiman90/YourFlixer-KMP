package navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import core.component.Component
import core.navigation.RootDestination
import core.navigation.TopLevelDestination
import features.downloads.DownloadsComponent
import features.home.HomeComponent
import features.homeroot.HomeRootComponent
import features.profile.ProfileComponent
import features.search.SearchComponent

interface MainNavigationComponent : Component {

    val childStack: Value<ChildStack<*, RootChild>>

    val showBottomBar: MutableValue<Boolean>

    var lastSelectedTabDestination: RootDestination

    //for custom back press
    fun onBackPressed()

    fun onHomeTabClicked()
    fun onSearchTabClicked()
    fun onDownloadTabClicked()
    fun onProfileTabClicked()

    fun onAppNavigationRailItemClicked(selectedDestination: TopLevelDestination)

    fun onBottomBarItemClicked(selectedDestination: TopLevelDestination)


    sealed class RootChild(val destination: RootDestination) {
        abstract val isFullscreen: Boolean


        data class HomeNavChild(
            val component: HomeRootComponent,
        ) : RootChild(destination) {
            companion object {
                val destination = TopLevelDestination.HOME
            }

            override val isFullscreen: Boolean
                get() = false
        }

        data class SearchNavChild(
            val component: SearchComponent,
        ) : RootChild(destination) {
            companion object {
                val destination = TopLevelDestination.SEARCH
            }

            override val isFullscreen: Boolean
                get() = false
        }


        data class DownloadsNavChild(
            val component: DownloadsComponent,
        ) : RootChild(destination) {
            companion object {
                val destination = TopLevelDestination.DOWNLOADS
            }

            override val isFullscreen: Boolean
                get() = false
        }

        data class ProfileNavChild(
            val component: ProfileComponent,
        ) : RootChild(destination) {
            companion object {
                val destination = TopLevelDestination.PROFILE
            }

            override val isFullscreen: Boolean
                get() = false
        }

    }

    sealed class Output {

        data class OpenStreamVideo(val itemId: Long) : Output()

    }
}