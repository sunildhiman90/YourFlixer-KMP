package features.stream

import com.arkivanov.decompose.ComponentContext
import utils.AppDispatchers
import utils.Consumer

class DefaultStreamVideoComponent(
    componentContext: ComponentContext,
    dispatchers: AppDispatchers,
    private val output: Consumer<StreamVideoComponent.Output>,
) : StreamVideoComponent, ComponentContext by componentContext {

    override fun onBackClicked() {
        output(StreamVideoComponent.Output.GoBack)
    }


}

class StreamVideoComponentFactory(
    private val dispatchers: AppDispatchers,
) {
    // Use those dependencies in create method which we can be changed during creating instance
    // But rest of the dependencies which will be not going to change and will be available before creating these component can be passed from Factory constructor,
    // For example component context, config, Output callbacks etc,
    // which we wil pass from root
    fun create(
        componentContext: ComponentContext,
        output: Consumer<StreamVideoComponent.Output>
    ) = DefaultStreamVideoComponent(componentContext, dispatchers, output)
}
