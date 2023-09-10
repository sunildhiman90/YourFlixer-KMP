/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package core.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.yourflixer.common.Res
import core.designsystem.icon.AppIcons
import utils.Strings

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). .
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: String,
    val titleText: String,
) : RootDestination {
    HOME(
        selectedIcon = AppIcons.HomeIcon,
        unselectedIcon = AppIcons.HomeIconSelected,
        iconText = Res.string.home,
        titleText = Res.string.home,
    ),
    SEARCH(
        selectedIcon = AppIcons.Search,
        unselectedIcon = AppIcons.SearchSelected,
        iconText = Res.string.search,
        titleText = Res.string.search,
    ),
    DOWNLOADS(
        selectedIcon = AppIcons.DownloadIcon,
        unselectedIcon = AppIcons.DownloadIconSelected,
        iconText = Res.string.downloads,
        titleText = Res.string.downloads,
    ),
    PROFILE(
        selectedIcon = AppIcons.ProfileCircle,
        unselectedIcon = AppIcons.ProfileCircleSelected,
        iconText = Res.string.profile,
        titleText = Res.string.profile,
    ),
}