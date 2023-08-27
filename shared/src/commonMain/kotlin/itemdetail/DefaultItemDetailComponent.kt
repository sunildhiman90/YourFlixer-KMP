package itemdetail


import com.arkivanov.decompose.ComponentContext
import utils.AppDispatchers
import utils.Consumer

class DefaultItemDetailComponent(
    componentContext: ComponentContext,
    dispatchers: AppDispatchers,
    private val itemId: Long?,
    private val goBack: (() -> Unit)? = null,
    output: Consumer<ItemDetailComponent.Output>
) : ItemDetailComponent, ComponentContext by componentContext {

    override fun onBackClicked() {
        goBack?.invoke()
    }

}


class ItemDetailComponentFactory(
    private val dispatchers: AppDispatchers,
) {
    fun create(
        componentContext: ComponentContext,
        itemId: Long?,
        goBack: (() -> Unit)? = null,
        output: Consumer<ItemDetailComponent.Output>
    ) = DefaultItemDetailComponent(componentContext, dispatchers, itemId, goBack, output)
}