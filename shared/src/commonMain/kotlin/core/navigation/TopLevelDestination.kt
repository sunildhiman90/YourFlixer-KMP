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
import core.designsystem.icon.AppIcons
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import your_flixer.shared.generated.resources.Res
import your_flixer.shared.generated.resources.downloads
import your_flixer.shared.generated.resources.home
import your_flixer.shared.generated.resources.profile
import your_flixer.shared.generated.resources.search

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). .
 */
enum class TopLevelDestination @OptIn(ExperimentalResourceApi::class) constructor(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: StringResource,
) : RootDestination {
    @OptIn(ExperimentalResourceApi::class)
    HOME(
        selectedIcon = AppIcons.HomeIconSelected,
        unselectedIcon = AppIcons.HomeIcon,
        iconText = Res.string.home,
    ),
    @OptIn(ExperimentalResourceApi::class)
    SEARCH(
        selectedIcon = AppIcons.SearchSelected,
        unselectedIcon = AppIcons.Search,
        iconText = Res.string.search,
    ),
    @OptIn(ExperimentalResourceApi::class)
    DOWNLOADS(
        selectedIcon = AppIcons.DownloadIconSelected,
        unselectedIcon = AppIcons.DownloadIcon,
        iconText = Res.string.downloads,
    ),
    @OptIn(ExperimentalResourceApi::class)
    PROFILE(
        selectedIcon = AppIcons.ProfileCircleSelected,
        unselectedIcon = AppIcons.ProfileCircle,
        iconText = Res.string.profile,
    ),
}