package itemdetail.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.designsystem.component.CommonTopAppBar
import core.designsystem.icon.AppIcon
import core.designsystem.icon.AppIcons
import data.TestData
import utils.CustomImage
import utils.Strings


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    onLinkClick: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    val horizontalPadding = 16.dp

    Scaffold(topBar = {
        CommonTopAppBar(
            titleComposable = {
                Text(
                    text = Strings.item_details,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            actionIcon1 = AppIcon.ImageVectorIcon(AppIcons.AddPostIcon),
            actionIcon2 = AppIcon.ImageVectorIcon(AppIcons.MenuIcon),
            navigationIcon = AppIcon.ImageVectorIcon(AppIcons.Back),
            onNavigationClick = onBackPressed,
            actionIconContentDescription = "",
            windowInsetsPadding = WindowInsets(0.dp),
            showActions = false
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {


        }

    }

}

@Composable
fun UserTabsScreen() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Home", "About", "Settings")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                UserCustomTabIndicator(tabPositions[tabIndex])
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    selectedContentColor = MaterialTheme.colorScheme.onBackground,
                    unselectedContentColor = MaterialTheme.colorScheme.outline,
                    icon = {
                        when (index) {
                            0 -> Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = null,
                            )

                            1 -> Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                            )

                            2 -> Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null,
                            )
                        }
                    }
                )
            }
        }
        when (tabIndex) {
            0 -> UserAccountTabHomeContent()
            1 -> Text(text = "Tab2")
            2 -> UserAccountTabTaggedContent()
        }
    }
}

@Composable
fun UserAccountTabHomeContent() {
    val searchGridImageHeight = 160.dp
    val searchGridImageWidth = 150.dp
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
    ) {
        items(TestData.searchItemsList) { item ->
            CustomImage(
                url = item.url,
                modifier = Modifier
                    .size(height = searchGridImageHeight, width = searchGridImageWidth)
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.background
                        ),
                        shape = RectangleShape
                    ),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
fun UserAccountTabTaggedContent() {
    val searchGridImageHeight = 160.dp
    val searchGridImageWidth = 150.dp
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
    ) {
        items(TestData.searchItemsList) { item ->
            CustomImage(
                url = item.url,
                modifier = Modifier
                    .size(height = searchGridImageHeight, width = searchGridImageWidth)
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.background
                        ),
                        shape = RectangleShape
                    ),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
fun UserCustomTabIndicator(
    currentTabPosition: TabPosition,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    val tabIndicatorHeight = 1.5.dp
    TabRowDefaults.Indicator(
        Modifier.tabIndicatorOffset(currentTabPosition),
        color = color,
        height = tabIndicatorHeight
    )
}

@Composable
fun UserProfileButton(title: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Text(text = title, style = MaterialTheme.typography.titleSmall)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ProfileScreenPreview() {
//    ProfileScreen({
//
//    })
//}

