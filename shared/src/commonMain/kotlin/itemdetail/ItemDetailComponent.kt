package itemdetail

import core.component.Component

interface ItemDetailComponent : Component {

    fun onBackClicked()

    sealed class Output {}
}