package theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp
import utils.dimens.Dimensions
import utils.dimens.compactDimensions

val Shapes = Shapes(
    small = RoundedCornerShape(compactDimensions.defaultShapeCornerRadius),
    medium = RoundedCornerShape(compactDimensions.defaultShapeCornerRadius),
    large = RoundedCornerShape(0.dp)
)
