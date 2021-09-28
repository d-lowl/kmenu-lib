package space.dlowl.kmenu.menu

enum class Backend(val command: String) {
    DMENU("dmenu"),
    ROFI("rofi -dmenu")
}