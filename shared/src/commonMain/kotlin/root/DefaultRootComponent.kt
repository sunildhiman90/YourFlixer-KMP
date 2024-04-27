package root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import core.navigation.RootDestination
import core.navigation.TopLevelDestination
import features.stream.StreamVideoComponent
import features.stream.StreamVideoComponentFactory
import kotlinx.serialization.Serializable
import navigation.DefaultMainNavigationComponent
import navigation.MainNavigationComponent
import utils.AppDispatchers


//TODO, add AppLogger in koin and setup Kermit also from koin

class DefaultRootComponent(
    componentContext: ComponentContext,
    val dispatchers: AppDispatchers,
    private val mainNavigationComponentFactory: DefaultMainNavigationComponent.Factory,
    private val streamVideoComponentFactory: StreamVideoComponentFactory,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val stack =
        childStack(
            source = navigation,
            initialStack = { getInitialStack() },
            serializer = Config.serializer(),
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

    @Serializable
    private sealed interface Config {
        @Serializable
        object MainNavigation : Config {
            /**
             * Only required for state preservation on JVM/desktop via StateKeeper, as it uses Serializable.
             * Temporary workaround for https://youtrack.jetbrains.com/issue/KT-40218.
             */
            @Suppress("unused")
            private fun readResolve(): Any = MainNavigation
        }

        @Serializable
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
                mainNavigationComponentFactory.create(
                    componentContext,
                    ::onNavOutput
                )
            )

            is Config.StreamVideo -> RootComponent.RootChild.StreamVideoChild(
                streamVideoComponentFactory.create(
                    componentContext,
                    ::onStreamVideoOutput,
                )
            )

        }


    private companion object {

        private fun getInitialStack(): List<Config> = listOf(Config.MainNavigation)

    }

    class Factory(
        private val dispatchers: AppDispatchers,
        private val mainNavigationComponentFactory: DefaultMainNavigationComponent.Factory,
        private val streamVideoComponentFactory: StreamVideoComponentFactory,
    ) {
        fun create(componentContext: ComponentContext) =
            DefaultRootComponent(
                componentContext,
                dispatchers,
                mainNavigationComponentFactory,
                streamVideoComponentFactory
            )
    }

}