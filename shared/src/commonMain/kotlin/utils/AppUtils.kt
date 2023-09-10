package utils

import androidx.compose.ui.unit.dp

/**
 * Different type of platforms supported by app.
 */
enum class AppPlatform {
    ANDROID, IOS, DESKTOP, WEB
}

/**
 * Different type of navigation supported by app depending on device size and state.
 */
enum class AppNavigationType {
    BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}

/**
 * Different position of navigation content inside Navigation Rail, Navigation Drawer depending on device size and state.
 */
enum class AppNavigationContentPosition {
    TOP, CENTER
}

/**
 * App Content shown depending on device size and state.
 */
enum class AppContentType {
    SINGLE_PANE, DUAL_PANE
}


enum class DeviceType {
    MOBILE, TABLET, DESKTOP
}


enum class LayoutType {
    HEADER, CONTENT
}

/**
 * For getting [AppNavigationType]
 */
fun getAppNavigationAndContentType(deviceInfo: DeviceInfo): Pair<AppNavigationType, AppContentType> {

    return when (deviceInfo.deviceTypeClass) {
        DeviceTypeClass.Mobile -> {
            AppNavigationType.BOTTOM_NAVIGATION to AppContentType.SINGLE_PANE
        }

        DeviceTypeClass.Tablet -> {
            AppNavigationType.NAVIGATION_RAIL to AppContentType.DUAL_PANE
        }

        DeviceTypeClass.Desktop -> {
            AppNavigationType.PERMANENT_NAVIGATION_DRAWER to AppContentType.DUAL_PANE
        }

        else -> {
            AppNavigationType.BOTTOM_NAVIGATION to AppContentType.SINGLE_PANE
        }
    }
}

//NO need for this, WindowInsets are available by default in compose 1.5.1
//val defaultIOSTopPadding = 40.dp
val defaultIOSTopPadding = 0.dp
val defaultIOSTopPaddingSearchScreen = 40.dp
