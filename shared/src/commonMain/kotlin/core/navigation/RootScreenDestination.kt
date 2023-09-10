package core.navigation

import com.yourflixer.common.Res

enum class RootScreenDestination(
    titleText: String,
) : RootDestination {

    MAIN_NAVIGATION(
        titleText = Res.string.home,
    ),
    STREAM_VIDEO(
        titleText = Res.string.watch,
    ),
    ITEM_DETAIL(
        titleText = Res.string.item_details,
    ),

}