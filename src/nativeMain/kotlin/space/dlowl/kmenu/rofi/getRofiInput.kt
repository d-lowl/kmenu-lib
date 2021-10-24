package space.dlowl.kmenu.rofi

import kotlinx.cinterop.toKString
import kotlinx.serialization.decodeFromString
import platform.posix.getenv
import kotlinx.serialization.json.Json

actual fun getRofiInput(): RofiInput {
    val state = RofiState.fromStateNumber(getenv("ROFI_RETV")?.toKString()?.toInt() ?: 0)
        ?: throw Exception("Rofi state is not supported")
    val pathString = getenv("ROFI_INFO")?.toKString()
    val path = if (pathString != null) Json.decodeFromString<List<RofiSelection>>(pathString) else listOf()
    if (state != RofiState.INITIAL_CALL && path == listOf<String>()) {
        throw Exception("Null path given for a non-initial call")
    }
    return RofiInput(state, path)
}