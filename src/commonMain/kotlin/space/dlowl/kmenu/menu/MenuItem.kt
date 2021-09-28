package space.dlowl.kmenu.menu

typealias MenuItemAction = () -> String

data class MenuItem(val label: String, val action: MenuItemAction = defaultAction(label)) {
    private constructor(builder: Builder) : this(builder.label!!, builder.action!!)

    companion object {
        fun defaultAction(label: String): MenuItemAction = { label }
    }

    class Builder {
        var label: String? = null
        var action: MenuItemAction? = null

        fun build(): MenuItem {
            checkNotNull(label)
            if (action == null) action = defaultAction(label!!)
            return MenuItem(this)
        }
    }
}
