package core.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import core.designsystem.icon.AppIcon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun CommonTopAppBar(
    titleComposable: @Composable () -> Unit,
    actionIcon1: AppIcon? = null,
    actionIcon2: AppIcon? = null,
    navigationIcon: AppIcon? = null,
    actionIconContentDescription: String? = "",
    navigationIconContentDescription: String? = "",
    modifier: Modifier = Modifier,
    onAction1Click: () -> Unit = {},
    onAction2Click: () -> Unit = {},
    onNavigationClick: () -> Unit = {},
    windowInsetsPadding: WindowInsets = WindowInsets(0.dp),
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface
    ),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    showActions: Boolean = true,
) {

    //in ios padding is required, because we have disabled the safe area from ios app,
//    val modifier = if (getPlatformName() == AppPlatform.IOS.name) modifier.windowInsetsPadding(
//            windowInsetsPadding
//        ).padding(top = defaultIOSTopPadding) else modifier.windowInsetsPadding(windowInsetsPadding)
//
    val modifier = modifier.windowInsetsPadding(windowInsetsPadding)

    val iconSize = 24.dp
    Column {
        TopAppBar(
            modifier = modifier,
            title = {
                titleComposable()
            },
            scrollBehavior = scrollBehavior,
            actions = {
                if (showActions) {
                    IconButton(
                        onClick = onAction1Click,

                        ) {
                        if (actionIcon1 is AppIcon.ImageVectorIcon) {
                            Icon(
                                modifier = Modifier
                                    .size(iconSize),
                                imageVector = actionIcon1.imageVector,
                                contentDescription = actionIconContentDescription,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        } else if (actionIcon1 is AppIcon.ImageResourceIcon) {
                            Icon(
                                modifier = Modifier
                                    .size(iconSize),
                                painter = painterResource(actionIcon1.id),
                                contentDescription = actionIconContentDescription,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    IconButton(
                        onClick = onAction2Click
                    ) {
                        if (actionIcon2 is AppIcon.ImageVectorIcon) {
                            Icon(
                                modifier = Modifier
                                    .size(iconSize),
                                imageVector = actionIcon2.imageVector,
                                contentDescription = actionIconContentDescription,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        } else if (actionIcon2 is AppIcon.ImageResourceIcon) {
                            Icon(
                                modifier = Modifier
                                    .size(iconSize),
                                painter = painterResource(actionIcon2.id),
                                contentDescription = actionIconContentDescription,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            },
            navigationIcon = {
                if (navigationIcon != null) {
                    IconButton(
                        onClick = onNavigationClick
                    ) {
                        if (navigationIcon is AppIcon.ImageVectorIcon) {
                            Icon(
                                modifier = Modifier
                                    .size(iconSize),
                                imageVector = navigationIcon.imageVector,
                                contentDescription = actionIconContentDescription,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        } else if (navigationIcon is AppIcon.ImageResourceIcon) {
                            Icon(
                                modifier = Modifier
                                    .size(iconSize),
                                painter = painterResource(navigationIcon.id),
                                contentDescription = actionIconContentDescription,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }

            },
            colors = colors,
        )
        Divider(thickness = 0.2.dp, color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun CommonTopAppBarProfile(
    titleComposable: @Composable () -> Unit,
    actionIcon1: AppIcon,
    actionIcon2: AppIcon,
    navigationIcon: ImageVector? = null,
    actionIconContentDescription: String? = "",
    navigationIconContentDescription: String? = "",
    modifier: Modifier = Modifier,
    onAction1Click: () -> Unit = {},
    onAction2Click: () -> Unit = {},
    onNavigationClick: () -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface
    ),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {

    Column {
        TopAppBar(
            modifier = modifier,
            title = {
                titleComposable()
            },
            scrollBehavior = scrollBehavior,
            actions = {
                /*Image(
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 8.dp),
                    painter = actionIcon,
                    contentDescription = actionIconContentDescription,
                    //colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )*/
                IconButton(
                    onClick = onAction1Click, modifier = Modifier.size(22.dp),
                ) {
                    if (actionIcon1 is AppIcon.ImageVectorIcon) {
                        Icon(
                            imageVector = actionIcon1.imageVector,
                            contentDescription = actionIconContentDescription,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    } else if (actionIcon1 is AppIcon.ImageResourceIcon) {
                        Icon(
                            painter = painterResource(actionIcon1.id),
                            contentDescription = actionIconContentDescription,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                }

                IconButton(onClick = onAction2Click) {
                    if (actionIcon2 is AppIcon.ImageVectorIcon) {
                        Icon(
                            imageVector = actionIcon2.imageVector,
                            contentDescription = actionIconContentDescription,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    } else if (actionIcon2 is AppIcon.ImageResourceIcon) {
                        Icon(
                            painter = painterResource(actionIcon2.id),
                            contentDescription = actionIconContentDescription,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            },
            /*navigationIcon = {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = navigationIconContentDescription,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },*/
            colors = colors
        )
        Divider(thickness = 0.2.dp, color = MaterialTheme.colorScheme.outlineVariant)
    }
}
