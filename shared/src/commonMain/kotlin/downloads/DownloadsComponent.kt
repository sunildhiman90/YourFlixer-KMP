package downloads

import core.component.Component

interface DownloadsComponent : Component {

    fun onBackClicked()

    sealed class Output {}
}