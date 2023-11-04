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
import com.yourflixer.common.MR
import core.designsystem.icon.AppIcons
import dev.icerock.moko.resources.StringResource

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). .
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: StringResource,
) : RootDestination {
    HOME(
        selectedIcon = AppIcons.HomeIconSelected,
        unselectedIcon = AppIcons.HomeIcon,
        iconText = MR.strings.home,
    ),
    SEARCH(
        selectedIcon = AppIcons.SearchSelected,
        unselectedIcon = AppIcons.Search,
        iconText = MR.strings.search,
    ),
    DOWNLOADS(
        selectedIcon = AppIcons.DownloadIconSelected,
        unselectedIcon = AppIcons.DownloadIcon,
        iconText = MR.strings.downloads,
    ),
    PROFILE(
        selectedIcon = AppIcons.ProfileCircleSelected,
        unselectedIcon = AppIcons.ProfileCircle,
        iconText = MR.strings.profile,
    ),
}