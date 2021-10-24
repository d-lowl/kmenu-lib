package space.dlowl.kmenu

import space.dlowl.kmenu.menu.Menu
import space.dlowl.kmenu.rofi.RofiInput
import space.dlowl.kmenu.rofi.RofiSelection
import space.dlowl.kmenu.rofi.RofiSelectionType
import space.dlowl.kmenu.rofi.RofiState

object Executor {
    fun execute(menu: Menu, rofiInput: RofiInput) {
        if (rofiInput.state == RofiState.INITIAL_CALL) {
            // Special case, the script is called for the first time
            displayMenu(menu)
            return
        }

        val lastMenu = navigate(menu, rofiInput)
        val lastSelection = rofiInput.path.last()
        if (lastSelection.type == RofiSelectionType.SUBMENU) {
            displayMenu(lastMenu.getSubmenu(lastSelection.value), rofiInput.path)
        } else if (lastSelection.type == RofiSelectionType.ENTRY) {
            lastMenu.executeEntry(lastSelection.value, rofiInput)
        }
    }

    fun navigate(menu: Menu, rofiInput: RofiInput): Menu {
        var lastMenu = menu
        rofiInput.path.dropLast(1).forEach {
            if (it.type != RofiSelectionType.SUBMENU) throw IllegalArgumentException()
            lastMenu = lastMenu.getSubmenu(it.value)
        }
        return lastMenu
    }

    fun displayMenu(_menu: Menu, prefix: List<RofiSelection> = listOf()) {
        _menu.getMenuOptions(prefix).forEach { println(it) }
    }
}