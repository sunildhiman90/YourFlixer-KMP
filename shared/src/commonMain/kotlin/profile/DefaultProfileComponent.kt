package profile

import com.arkivanov.decompose.ComponentContext
import utils.AppDispatchers
import utils.Consumer

class DefaultProfileComponent(
    componentContext: ComponentContext,
    dispatchers: AppDispatchers,
    private val userId: Long?,
    override val isBackEnabled: Boolean,
    private val goBack: (() -> Unit)? = null,
    output: Consumer<ProfileComponent.Output>,
) : ProfileComponent, ComponentContext by componentContext {

    override fun onBackClicked() {
        goBack?.invoke()
    }

    class Factory(
        private val dispatchers: AppDispatchers,
    ) {
        fun create(
            componentContext: ComponentContext,
            userId: Long?,
            isBackEnabled: Boolean,
            goBack: (() -> Unit)? = null,
            output: Consumer<ProfileComponent.Output>
        ) = DefaultProfileComponent(
            componentContext,
            dispatchers,
            userId,
            isBackEnabled,
            goBack,
            output
        )
    }
}
