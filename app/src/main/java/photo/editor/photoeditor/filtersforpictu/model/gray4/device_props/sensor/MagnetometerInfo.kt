package photo.editor.photoeditor.filtersforpictu.model.gray4.device_props.sensor

import photo.editor.photoeditor.filtersforpictu.model.gray4.device_props.core.Info
import photo.editor.photoeditor.filtersforpictu.model.gray4.device_props.core.DeviceMotionResult

class MagnetometerInfo : Info {
    override suspend fun collect(vararg args: Any?): String {
        return try {
            val deviceMotionResult = args[0] as DeviceMotionResult
            val score = deviceMotionResult.magScore ?: "undefined"
            "MAGN[$score]"
        } catch (e: Throwable) {
            "MAGN[undefined]"
        }
    }
}
