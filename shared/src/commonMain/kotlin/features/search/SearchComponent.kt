package features.search

import core.component.Component

interface SearchComponent : Component {

    //val childStack: Value<ChildStack<*, CounterComponent>>

    fun onBackClicked()


    sealed class Output {
        
        //data class OpenItemDetail(val itemId: Long) : Output()
    }
}