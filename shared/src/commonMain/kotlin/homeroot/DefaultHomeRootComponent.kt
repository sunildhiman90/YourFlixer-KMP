package homeroot

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import home.DefaultHomeComponent
import home.HomeComponent
import itemdetail.DefaultItemDetailComponent
import utils.Consumer

internal class DefaultHomeRootComponent(
    componentContext: ComponentContext,
    private val output: Consumer<HomeRootComponent.Output>
) : HomeRootComponent, ComponentContext by componentContext {


    private val navigation = StackNavigation<Config>()

    private val _childStack =
        childStack(
            source = navigation,
            initialStack = { getInitialStack() },
            childFactory = ::child,
            handleBackButton = true
        )

    private fun getInitialStack(): List<Config> = listOf(Config.Home)

    override val childStack: Value<ChildStack<*, HomeRootComponent.HomeChild>> get() = _childStack

    private fun child(
        config: Config,
        componentContext: ComponentContext,
    ): HomeRootComponent.HomeChild {
        return when (config) {
            is Config.Home -> HomeRootComponent.HomeChild.HomeMainChild(
                DefaultHomeComponent(
                    componentContext,
                    output = this::onHomeComponentOutput
                )
            )

            is Config.HomeItemDetail -> HomeRootComponent.HomeChild.HomeItemDetailChild(
                DefaultItemDetailComponent(
                    componentContext,
                    itemId = config.itemId,
                    goBack = {
                        onBackClicked()
                    }
                )
            )
        }
    }

    private fun onHomeComponentOutput(homeComponentOutput: HomeComponent.Output) {
        when (homeComponentOutput) {
            is HomeComponent.Output.OpenItemDetail -> {
                //Use this approach if want to Open ItemDetail in nested screen within home screen with bottom tabs
                onFeedItemClicked(
                    homeComponentOutput.itemId
                )
            }

            //Use this approach(pass Output to RootComponent) if we want to open StreamVideo in full screen without bottom tabs and use it from RootComponent
            /*output(
                HomeRootComponent.Output.OpenStreamVideo(output.userId, output.name)
            )*/
            is HomeComponent.Output.OpenStreamVideo -> {
                output(
                    HomeRootComponent.Output.OpenStreamVideo(homeComponentOutput.itemId)
                )
            }
        }
    }

    sealed interface Config : Parcelable {
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
        data class HomeItemDetail(val itemId: Long, val isBackEnabled: Boolean) : Config {
            /**
             * Only required for state preservation on JVM/desktop via StateKeeper, as it uses Serializable.
             * Temporary workaround for https://youtrack.jetbrains.com/issue/KT-40218.
             */
            @Suppress("unused")
            private fun readResolve(): Any = HomeItemDetail(itemId, isBackEnabled)
        }
    }

    override fun onBackClicked() {
        if (childStack.value.items.size > 1) {
            navigation.pop()
        }
    }

    override fun onFeedItemClicked(itemId: Long) {
        navigation.push(Config.HomeItemDetail(itemId, isBackEnabled = true))
    }

}
