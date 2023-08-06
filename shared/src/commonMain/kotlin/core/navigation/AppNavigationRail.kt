package core.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import utils.AppNavigationContentPosition
import utils.LayoutType

@Composable
fun AppNavigationRail(
    activeDestination: RootDestination,
    topLevelDestinations: List<TopLevelDestination>,
    navigateToTopLevelDestination: (TopLevelDestination) -> Unit,
    navigationContentPosition: AppNavigationContentPosition = AppNavigationContentPosition.TOP,
    onDrawerClicked: () -> Unit = {}
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        Layout(modifier = Modifier.widthIn(max = 80.dp), content = {
            Column(
                modifier = Modifier.layoutId(LayoutType.HEADER),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                NavigationRailItem(selected = false, onClick = onDrawerClicked, icon = {
                    Icon(
                        imageVector = Icons.Default.Menu, contentDescription = ""
                    )
                })
                Spacer(Modifier.height(8.dp)) // NavigationRailHeaderPadding
                Spacer(Modifier.height(4.dp)) // NavigationRailVerticalPadding
            }

            Column(
                modifier = Modifier.layoutId(LayoutType.CONTENT),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                topLevelDestinations.forEach { appDestination ->
                    NavigationRailItem(selected = activeDestination == appDestination,
                        onClick = {
                            navigateToTopLevelDestination(appDestination)
                        },
                        icon = {
                            Icon(
                                imageVector = appDestination.selectedIcon, contentDescription = ""
                            )
                        })
                }
            }
        }, measurePolicy = { measurables, constraints ->
            lateinit var headerMeasurable: Measurable
            lateinit var contentMeasurable: Measurable
            measurables.forEach {
                when (it.layoutId) {
                    LayoutType.HEADER -> headerMeasurable = it
                    LayoutType.CONTENT -> contentMeasurable = it
                    else -> error("Unknown layoutId encountered!")
                }
            }

            //TODO, need to read more about Custom Layout, measurables and placeables etc.
            val headerPlaceable = headerMeasurable.measure(constraints)
            val contentPlaceable = contentMeasurable.measure(
                constraints.offset(vertical = -headerPlaceable.height)
            )
            layout(constraints.maxWidth, constraints.maxHeight) {
                // Place the header, this goes at the top
                headerPlaceable.placeRelative(0, 0)

                // Determine how much space is not taken up by the content
                val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height

                val contentPlaceableY = when (navigationContentPosition) {
                    // Figure out the place we want to place the content, with respect to the
                    // parent (ignoring the header for now)
                    AppNavigationContentPosition.TOP -> 0
                    AppNavigationContentPosition.CENTER -> nonContentVerticalSpace / 2
                }
                    // And finally, make sure we don't overlap with the header.
                    .coerceAtLeast(headerPlaceable.height)

                contentPlaceable.placeRelative(0, contentPlaceableY)
            }
        })
    }
}