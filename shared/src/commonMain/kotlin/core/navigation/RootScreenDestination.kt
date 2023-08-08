package core.navigation

import utils.Strings

enum class RootScreenDestination(
    titleText: String,
) : RootDestination {

    MAIN_NAVIGATION(
        titleText = Strings.home,
    ),
    STREAM_VIDEO(
        titleText = Strings.watch,
    ),
    ITEM_DETAIL(
        titleText = Strings.item_details,
    ),

}