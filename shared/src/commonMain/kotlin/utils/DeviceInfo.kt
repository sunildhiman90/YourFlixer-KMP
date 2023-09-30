package utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.jvm.JvmInline

class DeviceInfo private constructor(val deviceTypeClass: DeviceTypeClass) {

    companion object {
        fun calculateFromWidth(width: Dp): DeviceInfo {
            return DeviceInfo(DeviceTypeClass.fromWidth(width))
        }
    }
}

@JvmInline
value class DeviceTypeClass private constructor(val value: Int) {

    companion object {
        val Compact = DeviceTypeClass(0)

        val Medium = DeviceTypeClass(1)

        val Expanded = DeviceTypeClass(2)

        internal fun fromWidth(width: Dp): DeviceTypeClass {
            return when {
                width > 840.dp -> Expanded
                width > 600.dp && width < 839.dp  -> Medium
                else -> Compact
            }
        }
    }

}