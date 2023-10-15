package utils.dimens

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


class Dimensions(
    val horizontalPadding: Dp,
    val mediumPadding: Dp,
    val halfPadding: Dp,
    val smallPadding: Dp,
    val searchBarRadius: Dp,
    val searchBarHeight: Dp,
    val iconSize: Dp,
    val iconSizeMedium: Dp,
    val navRailMaxWidth: Dp,
    val defaultShapeCornerRadius: Dp,
    val permanentDrawerMinWidth: Dp,
    val permanentDrawerMaxWidth: Dp,
    val homeFeedImageWidth: Dp,
    val homeFeedImageHeight: Dp,
    val defaultBorderWidth: Dp
)

val compactDimensions = Dimensions(
    horizontalPadding = 16.dp,
    mediumPadding = 12.dp,
    halfPadding = 8.dp,
    smallPadding = 4.dp,
    searchBarRadius = 12.dp,
    searchBarHeight = 35.dp,
    iconSize = 24.dp,
    iconSizeMedium = 20.dp,
    navRailMaxWidth = 80.dp,
    defaultShapeCornerRadius = 4.dp,
    permanentDrawerMinWidth = 180.dp,
    permanentDrawerMaxWidth = 270.dp,
    homeFeedImageWidth = 120.dp,
    homeFeedImageHeight = 160.dp,
    defaultBorderWidth = 1.dp
)

const val mediumDimScaleFactor = 1.2

val mediumDimensions = Dimensions(
    horizontalPadding = (compactDimensions.horizontalPadding.value * mediumDimScaleFactor).dp,
    mediumPadding = (compactDimensions.mediumPadding.value * mediumDimScaleFactor).dp,
    halfPadding = (compactDimensions.halfPadding.value * mediumDimScaleFactor).dp,
    smallPadding = (compactDimensions.smallPadding.value * mediumDimScaleFactor).dp,
    searchBarRadius = (compactDimensions.searchBarRadius.value * mediumDimScaleFactor).dp,
    searchBarHeight = (compactDimensions.searchBarHeight.value * mediumDimScaleFactor).dp,
    iconSize = (compactDimensions.iconSize.value * mediumDimScaleFactor).dp,
    iconSizeMedium = (compactDimensions.iconSizeMedium.value * mediumDimScaleFactor).dp,
    navRailMaxWidth = (compactDimensions.navRailMaxWidth.value).dp,
    defaultShapeCornerRadius = (compactDimensions.defaultShapeCornerRadius.value * mediumDimScaleFactor).dp,
    permanentDrawerMinWidth = (compactDimensions.permanentDrawerMinWidth.value).dp,
    permanentDrawerMaxWidth = (compactDimensions.permanentDrawerMaxWidth.value).dp,
    homeFeedImageWidth = (compactDimensions.homeFeedImageWidth.value * mediumDimScaleFactor).dp,
    homeFeedImageHeight = (compactDimensions.homeFeedImageHeight.value * mediumDimScaleFactor).dp,
    defaultBorderWidth = (compactDimensions.defaultBorderWidth.value * mediumDimScaleFactor).dp
)


const val expandedDimScaleFactor = 1.4

val expandedDimensions = Dimensions(
    horizontalPadding = (compactDimensions.horizontalPadding.value * expandedDimScaleFactor).dp,
    mediumPadding = (compactDimensions.mediumPadding.value * expandedDimScaleFactor).dp,
    halfPadding = (compactDimensions.halfPadding.value * expandedDimScaleFactor).dp,
    smallPadding = (compactDimensions.smallPadding.value * expandedDimScaleFactor).dp,
    searchBarRadius = (compactDimensions.searchBarRadius.value * expandedDimScaleFactor).dp,
    searchBarHeight = (compactDimensions.searchBarHeight.value * expandedDimScaleFactor).dp,
    iconSize = (compactDimensions.iconSize.value * expandedDimScaleFactor).dp,
    iconSizeMedium = (compactDimensions.iconSizeMedium.value * expandedDimScaleFactor).dp,
    navRailMaxWidth = (compactDimensions.navRailMaxWidth.value).dp,
    defaultShapeCornerRadius = (compactDimensions.defaultShapeCornerRadius.value * expandedDimScaleFactor).dp,
    permanentDrawerMinWidth = (compactDimensions.permanentDrawerMinWidth.value).dp,
    permanentDrawerMaxWidth = (compactDimensions.permanentDrawerMaxWidth.value).dp,
    homeFeedImageWidth = (compactDimensions.homeFeedImageWidth.value * expandedDimScaleFactor).dp,
    homeFeedImageHeight = (compactDimensions.homeFeedImageHeight.value * expandedDimScaleFactor).dp,
    defaultBorderWidth = (compactDimensions.defaultBorderWidth.value * expandedDimScaleFactor).dp
)
