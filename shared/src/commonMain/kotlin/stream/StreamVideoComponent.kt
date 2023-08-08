package stream

import core.component.Component

interface StreamVideoComponent : Component {

    fun onBackClicked()

    sealed class Output {
        object GoBack : Output()
    }

}