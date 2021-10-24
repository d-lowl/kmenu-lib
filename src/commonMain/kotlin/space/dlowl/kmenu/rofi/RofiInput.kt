package space.dlowl.kmenu.rofi

expect fun getRofiInput(): RofiInput

data class RofiInput(val state: RofiState, val path: List<RofiSelection>) {
    fun next(): RofiInput {
        if (path.isEmpty()) {
            throw Exception("Cannot get next input for a terminal input")
        }
        val nextPath = path.drop(1)
        return RofiInput(state, nextPath)
    }

    fun isTerminal() = path.isEmpty()
}
