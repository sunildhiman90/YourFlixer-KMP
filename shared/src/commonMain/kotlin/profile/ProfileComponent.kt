package profile

import core.component.Component

interface ProfileComponent : Component {

    val isBackEnabled: Boolean

    //val parentComponent: Component

    //val childStack: Value<ChildStack<*, CounterComponent>>

    fun onBackClicked()

    sealed class Output {}
}