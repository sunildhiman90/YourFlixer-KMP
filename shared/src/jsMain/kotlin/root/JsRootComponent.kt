package root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import core.component.Component
import core.navigation.RootDestination
import core.navigation.RootScreenDestination
import core.navigation.TopLevelDestination
import downloads.DownloadsComponent
import home.HomeComponent
import itemdetail.ItemDetailComponent
import profile.ProfileComponent
import search.SearchComponent
import stream.StreamVideoComponent


interface JsRootComponent : Component {

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
            val component: HomeComponent,
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


        data class StreamVideoChild(
            val component: StreamVideoComponent,
        ) : RootChild(destination) {
            companion object {
                val destination = RootScreenDestination.STREAM_VIDEO
            }

            override val isFullscreen: Boolean
                get() = true
        }

        data class ItemDetailChild(
            val component: ItemDetailComponent,
        ) : RootChild(destination) {
            companion object {
                val destination = RootScreenDestination.ITEM_DETAIL
            }

            override val isFullscreen: Boolean
                get() = false
        }
    }

}