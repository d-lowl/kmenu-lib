package space.dlowl.kmenu.rofi

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

actual fun getRofiInput(): RofiInput {
    val state = RofiState.fromStateNumber(System.getenv("ROFI_RETV").toInt())
        ?: throw Exception("Rofi state is not supported")
    val pathString = System.getenv("ROFI_INFO")
    val path = Json.decodeFromString<List<RofiSelection>>(pathString)
    if (state != RofiState.INITIAL_CALL && path == listOf<String>()) {
        throw Exception("Null path given for a non-initial call")
    }
    return RofiInput(state, path)

}