package utils

import androidx.compose.ui.geometry.Rect


/**
 * Creates a new instance of [androidx.compose.ui.geometry.Rect] with the same bounds
 * specified in the given [android.graphics.Rect]
 */
fun Rect.toComposeRect(): Rect =
    Rect(
        this.left.toFloat(),
        this.top.toFloat(),
        this.right.toFloat(),
        this.bottom.toFloat()
    )