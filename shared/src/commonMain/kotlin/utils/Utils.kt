package utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.rememberAsyncImagePainter

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
    CompositionLocalProvider(
        LocalImageLoader provides generateImageLoader(),
    ) {
        val painter = rememberAsyncImagePainter(url)
        Image(
            painter,
            modifier = modifier,
            contentDescription = contentDescription,
            contentScale = contentScale,
        )
    }
}