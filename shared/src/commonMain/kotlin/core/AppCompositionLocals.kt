package core

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import utils.dimens.Dimensions

data class ComposeScreenConfiguration(val width: Dp, val height: Dp)

val LocalComposeScreenConfiguration =
    staticCompositionLocalOf<ComposeScreenConfiguration> { noLocalProvidedFor("LocalComposeScreenConfiguration") }



val LocalDimensions =
    staticCompositionLocalOf<Dimensions> { noLocalProvidedFor("LocalDimensions") }


private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}



