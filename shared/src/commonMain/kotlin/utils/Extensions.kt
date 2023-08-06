package utils

import androidx.compose.ui.unit.dp
import core.ComposeScreenConfiguration


object ResponsiveHelper {

    val isTablet: Boolean
        get() = isTablet

    val isDesktop: Boolean
        get() = isDesktop

    val isMobile: Boolean
        get() = isMobile
}


fun ComposeScreenConfiguration.isTablet(): Boolean {
    return this.width > 730.dp
}

fun ComposeScreenConfiguration.isDesktop(): Boolean {
    return this.width > 1200.dp
}

fun ComposeScreenConfiguration.isMobile(): Boolean {
    return !this.isTablet() && !this.isDesktop()
}
