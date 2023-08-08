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
        val Mobile = DeviceTypeClass(0)

        val Tablet = DeviceTypeClass(1)

        val Desktop = DeviceTypeClass(2)

        internal fun fromWidth(width: Dp): DeviceTypeClass {
            return when {
                width > 1200.dp -> Desktop
                width > 730.dp -> Tablet
                else -> Mobile
            }
        }
    }

}