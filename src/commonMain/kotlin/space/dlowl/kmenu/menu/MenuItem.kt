package space.dlowl.kmenu.menu

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import space.dlowl.kmenu.rofi.RofiInput
import space.dlowl.kmenu.rofi.RofiSelection
import space.dlowl.kmenu.rofi.RofiSelectionType

/**
 * MenuItemAction is a function type from the selection string to Unit
 * The action is executed when a [MenuItem] is selected
 */
typealias MenuItemFunc = (RofiInput) -> Unit

data class MenuItem(
    val key: String,
    val label: String,
    val nonselectable: Boolean,
    val meta: String,
    val func: MenuItemFunc?,
    val submenu: Menu?
) {
    constructor(key: String, label: String, func: MenuItemFunc) : this(key, label, false, "", func, null)

    constructor(key: String, label: String, submenu: Menu) : this(key, label, false, "", null, submenu)

    fun execute(input: RofiInput) {
        if (func == null) throw Exception("This menu item is not executable")
        func!!(input)
    }

    fun navigate(): Menu {
        if (submenu == null) throw Exception("This menu item is not a submenu item")
        return submenu
    }

    fun isSubmenu(): Boolean = submenu != null
    fun isEntry(): Boolean = func != null

    val type: RofiSelectionType
        get() =
            if(isSubmenu()) RofiSelectionType.SUBMENU
            else if(isEntry()) RofiSelectionType.ENTRY
            else RofiSelectionType.ENTRY

    val selection: RofiSelection
        get() = RofiSelection(type, key)

    fun getPathTo(prefix: List<RofiSelection>): List<RofiSelection> = prefix + listOf(selection)

    fun getPathToString(prefix: List<RofiSelection>): String =
        Json.encodeToString(ListSerializer(RofiSelection.serializer()), getPathTo(prefix))

    fun getRofiString(prefix: List<RofiSelection>): String =
        "${label}\u0000info\u001f${getPathToString(prefix)}\u001fmeta\u001f${meta}\u001fnonselectable\u001f${nonselectable}"

    private constructor(builder: Builder) :
            this(builder.key!!, builder.label!!, builder.nonselectable, builder.meta, builder.func, builder.submenu)

    companion object {
        fun defaultAction(label: String): MenuItemFunc = { }

        fun submenuAction(submenu: Menu): MenuItemFunc = {
//            input -> submenu.execute(input.next())
        }
    }

    class Builder {
        var key: String? = null
        var label: String? = null
        var func: MenuItemFunc? = null
        var nonselectable: Boolean = false
        var meta: String = ""
        var submenu: Menu? = null

        fun build(): MenuItem {
            if (nonselectable) {
                key = ""
            }
            checkNotNull(key)
            checkNotNull(label)
            return MenuItem(this)
        }
    }
}
