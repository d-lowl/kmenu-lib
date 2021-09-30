package space.dlowl.kmenu.menu

/**
 * MenuItemAction is a function type from the selection string to Unit
 * The action is executed when a [MenuItem] is selected
 */
typealias MenuItemAction = (String) -> Unit

data class MenuItem(val key: String, val label: String, val action: MenuItemAction = defaultAction(label)) {
    private constructor(builder: Builder) : this(builder.key!!, builder.label!!, builder.action!!)

    companion object {
        fun defaultAction(label: String): MenuItemAction = { }

        fun submenuAction(submenu: Menu): MenuItemAction = {
            selection ->
            val nextSelection = selection.split("/").drop(1).joinToString("/")
            submenu.main(if (nextSelection != "") arrayOf(nextSelection) else arrayOf())
        }
    }

    class Builder {
        var key: String? = null
        var label: String? = null
        var action: MenuItemAction? = null

        fun build(): MenuItem {
            checkNotNull(key)
            checkNotNull(label)
            if (action == null) action = defaultAction(label!!)
            return MenuItem(this)
        }
    }
}
