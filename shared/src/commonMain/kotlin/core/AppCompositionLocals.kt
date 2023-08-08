package core

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

data class ComposeScreenConfiguration(val width: Dp, val height: Dp)


val LocalComposeScreenConfiguration =
    staticCompositionLocalOf<ComposeScreenConfiguration> { noLocalProvidedFor("LocalComposeScreenConfiguration") }

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}