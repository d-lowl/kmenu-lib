package space.dlowl.kmenu.menu

import space.dlowl.kmenu.utils.executeCommand

object DMenu {
    fun show(title: String, options: List<String>? = null): String = when(options) {
        null -> executeCommand("echo -e \"\" | dmenu -p '$title'")
        else -> executeCommand("echo -e \"${options.joinToString("\n")}\" | dmenu -p '$title'")
    }
}

