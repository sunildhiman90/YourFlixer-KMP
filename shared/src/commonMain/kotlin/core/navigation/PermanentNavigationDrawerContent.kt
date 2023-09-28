package core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import dev.icerock.moko.resources.compose.stringResource
import utils.AppNavigationContentPosition
import utils.LayoutType
import utils.Strings
import utils.dimens.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermanentNavigationDrawerContent(
    activeDestination: RootDestination,
    navigationContentPosition: AppNavigationContentPosition,
    topLevelDestinations: List<TopLevelDestination>,
    navigateToTopLevelDestination: (TopLevelDestination) -> Unit,
) {
    PermanentDrawerSheet(modifier = Modifier.sizeIn(minWidth = Dimensions.permanentDrawerMinWidth, maxWidth = Dimensions.permanentDrawerMinWidth)) {
        // TODO remove custom nav drawer content positioning when NavDrawer component supports it. ticket : b/232495216
        Layout(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(Dimensions.horizontalPadding),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(Dimensions.smallPadding)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(Dimensions.horizontalPadding),
                        text = Strings.app,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
//                    ExtendedFloatingActionButton(
//                        onClick = { /*TODO*/ },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 8.dp, bottom = 40.dp),
//                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
//                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Edit,
//                            contentDescription = stringResource(id = R.string.edit),
//                            modifier = Modifier.size(18.dp)
//                        )
//                        Text(
//                            text = stringResource(id = R.string.compose),
//                            modifier = Modifier.weight(1f),
//                            textAlign = TextAlign.Center
//                        )
//                    }
                }

                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.CONTENT)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    topLevelDestinations.forEach { appDestination ->
                        NavigationDrawerItem(
                            selected = activeDestination == appDestination,
                            label = {
                                Text(
                                    text = stringResource(appDestination.iconText),
                                    modifier = Modifier.padding(horizontal = Dimensions.horizontalPadding)
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = appDestination.selectedIcon,
                                    contentDescription = stringResource(appDestination.iconText)
                                )
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent
                            ),
                            onClick = { navigateToTopLevelDestination(appDestination) }
                        )
                    }
                }
            },
            measurePolicy = { measurables, constraints ->
                lateinit var headerMeasurable: Measurable
                lateinit var contentMeasurable: Measurable
                measurables.forEach {
                    when (it.layoutId) {
                        LayoutType.HEADER -> headerMeasurable = it
                        LayoutType.CONTENT -> contentMeasurable = it
                        else -> error("Unknown layoutId encountered!")
                    }
                }

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
            }
        )
    }
}