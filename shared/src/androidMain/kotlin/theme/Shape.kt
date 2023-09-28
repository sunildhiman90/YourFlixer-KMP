package theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp
import utils.dimens.Dimensions

val Shapes = Shapes(
    small = RoundedCornerShape(Dimensions.defaultShapeCornerRadius),
    medium = RoundedCornerShape(Dimensions.defaultShapeCornerRadius),
    large = RoundedCornerShape(0.dp)
)
