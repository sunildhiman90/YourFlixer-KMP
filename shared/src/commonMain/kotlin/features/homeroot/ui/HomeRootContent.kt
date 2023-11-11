package features.homeroot.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.isFront
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import features.home.ui.HomeContent
import features.homeroot.HomeRootComponent
import features.itemdetail.ui.ItemDetailContent
import utils.AppNavigationType

@Composable
internal fun HomeRootContent(
    component: HomeRootComponent,
    appNavigationType: AppNavigationType,
    modifier: Modifier = Modifier
) {
    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation { _, _, direction ->
            if (direction.isFront) {
                slide() + fade()
            } else {
                scale(frontFactor = 1F, backFactor = 0.7F) + fade()
            }
        },
    ) {
        when (val child = it.instance) {
            is HomeRootComponent.HomeChild.HomeMainChild -> HomeContent(child.component, appNavigationType = appNavigationType)
            is HomeRootComponent.HomeChild.HomeItemDetailChild -> ItemDetailContent(child.component)
        }
    }
}

@Preview
@Composable
internal fun HomePreview() {
    HomeRootContent(component = PreviewHomeRootComponent(), appNavigationType = AppNavigationType.BOTTOM_NAVIGATION)
}

internal class PreviewHomeRootComponent : HomeRootComponent {

    override val childStack: Value<ChildStack<*, HomeRootComponent.HomeChild>>
        get() = TODO("Not yet implemented")


    override fun onBackClicked() {}
    override fun onFeedItemClicked(userId: Long) {
        TODO("Not yet implemented")
    }


}
