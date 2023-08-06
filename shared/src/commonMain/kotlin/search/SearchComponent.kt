package search

import core.component.Component

interface SearchComponent : Component {

    //val childStack: Value<ChildStack<*, CounterComponent>>

    fun onBackClicked()
}