package features.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import features.search.SearchComponent

@Composable
internal fun SearchContent(component: SearchComponent, modifier: Modifier = Modifier) {
//    Children(
//        stack = component.childStack,
//        modifier = modifier,
//        animation = stackAnimation { _, _, direction ->
//            if (direction.isFront) {
//                slide() + fade()
//            } else {
//                scale(frontFactor = 1F, backFactor = 0.7F) + fade()
//            }
//        },
//    ) {
//        CounterContent(
//            component = it.instance,
//            modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
//        )
//    }

    SearchScreen()
}

//@Preview
@Composable
internal fun SearchPreview() {
    SearchContent(component = PreviewSearchComponent())
}

internal class PreviewSearchComponent : SearchComponent {
//    override val childStack: Value<ChildStack<*, CounterComponent>> =
//        MutableValue(
//            ChildStack(
//                configuration = Unit,
//                instance = PreviewCounterComponent(),
//            )
//        )

    override fun onBackClicked() {}
}
