package search

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import utils.AppDispatchers
import utils.Consumer

class DefaultSearchComponent(
    componentContext: ComponentContext,
    dispatchers: AppDispatchers,
    output: Consumer<SearchComponent.Output>
) : SearchComponent, ComponentContext by componentContext {

//    private val navigation = StackNavigation<Config>()
//
//    private val _childStack =
//        childStack(
//            source = navigation,
//            initialConfiguration = Config(index = 0, isBackEnabled = false),
//            childFactory = ::child,
//        )
//
//    override val childStack: Value<ChildStack<*, CounterComponent>> get() = _childStack
//
//    private fun child(
//        config: Config,
//        componentContext: ComponentContext,
//    ): CounterComponent =
//        DefaultCounterComponent(
//            componentContext = componentContext,
//            title = "Counter ${config.index}",
//            isBackEnabled = config.isBackEnabled,
//            onNext = { navigation.push(Config(index = config.index + 1, isBackEnabled = true)) },
//            onPrev = navigation::pop,
//        )


    @Parcelize
    private data class Config(
        val index: Int,
        val isBackEnabled: Boolean,
    ) : Parcelable

    override fun onBackClicked() {
        //navigation.pop()
    }

    class Factory(
        private val dispatchers: AppDispatchers,
    ) {
        fun create(
            componentContext: ComponentContext,
            output: Consumer<SearchComponent.Output>
        ) = DefaultSearchComponent(componentContext, dispatchers, output)
    }
}
