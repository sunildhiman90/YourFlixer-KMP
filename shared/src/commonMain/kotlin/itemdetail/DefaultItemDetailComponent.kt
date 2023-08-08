package itemdetail


import com.arkivanov.decompose.ComponentContext

internal class DefaultItemDetailComponent(
    componentContext: ComponentContext,
    private val itemId: Long?,
    private val goBack: (() -> Unit)? = null
) : ItemDetailComponent, ComponentContext by componentContext {

    override fun onBackClicked() {
        goBack?.invoke()
    }
}
