package core.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.navigation.RootDestination
import core.navigation.TopLevelDestination
import dev.icerock.moko.resources.compose.stringResource
import getPlatformName
import utils.AppPlatform


@Composable
fun CommonAppBottomBar(
    modifier: Modifier,
    windowInsetsPadding: WindowInsets,
    activeDestination: RootDestination,
    lastActiveTabDestination: RootDestination,
    topLevelDestinations: List<TopLevelDestination>,
    navigateToTopLevelDestination: (TopLevelDestination) -> Unit,
) {

    val windowInsetsPadding =
        if (getPlatformName() == AppPlatform.IOS.name) WindowInsets(bottom = 16.dp) else windowInsetsPadding

    BottomAppBar(
        modifier = modifier,
        windowInsets = windowInsetsPadding,
    ) {

        topLevelDestinations.forEach { appDestination ->

            TabNavigationItem(
                selected = lastActiveTabDestination == appDestination,
                onClick = {
                    navigateToTopLevelDestination(appDestination)
                },
                icon = {
                    androidx.compose.material3.Icon(
                        imageVector = appDestination.selectedIcon,
                        contentDescription = stringResource(appDestination.iconText)
                    )
                },
            )
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(
    selected: Boolean,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {

    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
    )
}