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

package core.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.designsystem.icon.AppIcons
import utils.dimens.Dimensions

@Composable
fun SearchBar(title: String) {
    val horizontalPadding = Dimensions.horizontalPadding
    val mediumPadding = Dimensions.mediumPadding
    val searchBarRadius = Dimensions.searchBarRadius
    val searchBarHeight = Dimensions.searchBarHeight
    Surface(
        color = MaterialTheme.colorScheme.inverseOnSurface,
        modifier = Modifier
            .padding(
                start = horizontalPadding,
                end = horizontalPadding,
                top = horizontalPadding,
                bottom = mediumPadding
            )
            .clip(shape = RoundedCornerShape(size = searchBarRadius))
    ) {
        Row(
            modifier = Modifier
                .height(searchBarHeight)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = AppIcons.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(horizontal = horizontalPadding)
                    .size(Dimensions.iconSizeMedium)
            )
            Text(text = title, style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray))
        }
    }
}
