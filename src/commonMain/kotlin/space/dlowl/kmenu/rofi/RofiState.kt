package space.dlowl.kmenu.rofi

enum class RofiState(val stateNumber: Int) {
    INITIAL_CALL(0),
    ENTRY_SELECTED(1),
    CUSTOM_ENTRY_SELECTED(2);

    companion object {
        private val map = values().associateBy(RofiState::stateNumber)
        fun fromStateNumber(stateNumber: Int) = map[stateNumber]
    }
}