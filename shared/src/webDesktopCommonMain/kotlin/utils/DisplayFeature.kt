package utils

import androidx.compose.ui.geometry.Rect

/**
 * Description of a physical feature on the display.
 *
 *
 * A display feature is a distinctive physical attribute located within the display panel of
 * the device. It can intrude into the application window space and create a visual distortion,
 * visual or touch discontinuity, make some area invisible or create a logical divider or
 * separation in the screen space.
 *
 * @see FoldingFeature Represents a screen fold that intersects the application window.
 */
interface DisplayFeature {
    /**
     * The bounding rectangle of the feature within the application window
     * in the window coordinate space.
     */
    val bounds: Rect
}