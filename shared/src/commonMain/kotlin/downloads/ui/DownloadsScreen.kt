package downloads.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yourflixer.common.Res
import core.designsystem.component.CommonTopAppBar
import data.TestData
import kotlinx.coroutines.launch
import utils.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadsScreen() {
    val horizontalPadding = 16.dp

    Scaffold(topBar = {
        Column {
            CommonTopAppBar(
                titleComposable = {
                    Text(
                        text = Res.string.downloads,
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
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(vertical = horizontalPadding),
            modifier = Modifier.padding(innerPadding).draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                },
            ),
        ) {
            items(items = TestData.downloadItemsList, key = { item ->
                item.id
            }) { item ->
//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Text("Downloaded Item: ${item.title}")
//                }
            }
        }
    }
}

