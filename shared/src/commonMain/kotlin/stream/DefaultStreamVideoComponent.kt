package stream

import com.arkivanov.decompose.ComponentContext
import utils.Consumer

internal class DefaultStreamVideoComponent(
    componentContext: ComponentContext,
    private val output: Consumer<StreamVideoComponent.Output>,
) : StreamVideoComponent, ComponentContext by componentContext {

    override fun onBackClicked() {
        output(StreamVideoComponent.Output.GoBack)
    }
}
