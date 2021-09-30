package space.dlowl.kmenu.menu

typealias MenuItemAction = () -> Unit

data class MenuItem(val key: String, val label: String, val action: MenuItemAction = defaultAction(label)) {
    private constructor(builder: Builder) : this(builder.key!!, builder.label!!, builder.action!!)

    companion object {
        fun defaultAction(label: String): MenuItemAction = { }
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
