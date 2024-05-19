package core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.navigation.RootDestination
import core.navigation.TopLevelDestination
import getPlatformName
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import utils.AppPlatform


@OptIn(ExperimentalResourceApi::class)
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
                selectedIcon = {
                    androidx.compose.material3.Icon(
                        imageVector = appDestination.selectedIcon,
                        contentDescription = stringResource(appDestination.iconText)
                    )
                },
                unselectedIcon = {
                    androidx.compose.material3.Icon(
                        imageVector = appDestination.unselectedIcon,
                        contentDescription = stringResource(appDestination.iconText)
                    )
                }
            )
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(
    selected: Boolean,
    selectedIcon: @Composable () -> Unit,
    unselectedIcon: @Composable () -> Unit,
    onClick: () -> Unit
) {

    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else unselectedIcon,
    )
}