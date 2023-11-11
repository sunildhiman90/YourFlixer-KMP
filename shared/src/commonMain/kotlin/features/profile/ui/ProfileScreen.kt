package features.profile.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.LocalDimensions
import core.designsystem.component.CommonTopAppBar
import utils.AppNavigationType
import utils.Strings


/**
 * @param isNonTabProfile will be used to check if profile screen is being used from tab or from home or search
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    isNonTabProfile: Boolean,
    onLinkClick: (String) -> Unit,
    onBackPressed: () -> Unit,
    appNavigationType: AppNavigationType
) {
    val horizontalPadding = LocalDimensions.current.horizontalPadding

    Scaffold(topBar = {
        Column {
            CommonTopAppBar(
                titleComposable = {
                    Text(
                        text = Strings.profile,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = null,
                actionIconContentDescription = "",
                windowInsetsPadding = WindowInsets(0.dp),
                showActions = false
            )
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Profile")
        }
    }

}
