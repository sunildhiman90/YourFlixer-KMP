package root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import core.component.DeepLink
import core.navigation.RootDestination
import core.navigation.TopLevelDestination
import downloads.DefaultDownloadsComponent
import home.DefaultHomeComponent
import home.HomeComponent
import itemdetail.DefaultItemDetailComponent
import navigation.DefaultMainNavigationComponent
import navigation.MainNavigationComponent
import profile.DefaultProfileComponent
import search.DefaultSearchComponent
import stream.DefaultStreamVideoComponent
import stream.StreamVideoComponent
import utils.Consumer

open class DefaultRootComponent(
    componentContext: ComponentContext,
    private val homeComponent: (
        context: ComponentContext,
        Consumer<HomeComponent.Output>,
    ) -> HomeComponent,
    private val streamVideoComponent: (
        context: ComponentContext,
        output: Consumer<StreamVideoComponent.Output>
    ) -> StreamVideoComponent,
) : RootComponent, ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
    ) : this(
        componentContext,
        homeComponent = { homeComponentContext, homeComponentOutput ->
            DefaultHomeComponent(
                homeComponentContext,
                homeComponentOutput
            )
        },
        streamVideoComponent = { streamVideoComponentContext, output ->
            DefaultStreamVideoComponent(
                streamVideoComponentContext,
                output = output,
            )
        }
    )

    private val navigation = StackNavigation<Config>()

    private val stack =
        childStack(
            source = navigation,
            initialStack = { getInitialStack() },
            childFactory = ::child,
            handleBackButton = true,
        )

    override val childStack: Value<ChildStack<*, RootComponent.RootChild>> = stack

    override val showBottomBar: MutableValue<Boolean>
        get() = MutableValue(true)

    override var lastSelectedTabDestination: RootDestination = TopLevelDestination.HOME


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


    private fun onNavOutput(output: MainNavigationComponent.Output) {
        when (output) {
            is MainNavigationComponent.Output.OpenStreamVideo -> {
                navigation.push(
                    Config.StreamVideo(output.itemId)
                )
            }
        }
    }

    private fun onStreamVideoOutput(output: StreamVideoComponent.Output) {
        when (output) {
            StreamVideoComponent.Output.GoBack -> onBackPressed()
        }
    }

    private sealed interface Config : Parcelable {
        @Parcelize
        object MainNavigation : Config {
            /**
             * Only required for state preservation on JVM/desktop via StateKeeper, as it uses Serializable.
             * Temporary workaround for https://youtrack.jetbrains.com/issue/KT-40218.
             */
            @Suppress("unused")
            private fun readResolve(): Any = MainNavigation
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
    ): RootComponent.RootChild =
        when (config) {
            is Config.MainNavigation -> RootComponent.RootChild.MainNavChild(
                DefaultMainNavigationComponent(
                    componentContext,
                    ::onNavOutput
                )
            )

            is Config.StreamVideo -> RootComponent.RootChild.StreamVideoChild(
                streamVideoComponent(
                    componentContext,
                    ::onStreamVideoOutput,
                )
            )

        }


    private companion object {

        private fun getInitialStack(): List<Config> = listOf(Config.MainNavigation)

    }

}