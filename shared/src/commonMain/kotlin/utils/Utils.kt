package utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.rememberImagePainter

//TODO
//fun colorFromHex(color: String) = Color(android.graphics.Color.parseColor(color))

//If we need to change library in future, it will not affect ui , it will be changes here only
@Composable
fun CustomImage(
    url: String,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Fit,
    modifier: Modifier
) {

    //TODO, use coil instead of compose image loader, becoz compose image loader have some issues: its using ktor 2.3.11 which does not support wasm
    CompositionLocalProvider(
        LocalImageLoader provides generateImageLoader(),
    ) {
        val painter = rememberImagePainter(url)
        Image(
            painter,
            modifier = modifier,
            contentDescription = contentDescription,
            contentScale = contentScale,
        )
    }
}