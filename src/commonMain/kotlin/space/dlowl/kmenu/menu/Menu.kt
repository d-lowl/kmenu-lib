package space.dlowl.kmenu.menu

data class Menu(
    val title: String,
    val key: String? = null,
    val options: List<MenuItem>,
    val prefix: String = "",
) {
    private var optionMap: Map<String, MenuItemAction> = options.map { it.key to it.action }.toMap()
    private constructor(builder: Builder) : this(builder.title, builder.key, builder.options)

    protected fun getMenuOptions(): List<String> = options.map {
        "${it.label}\u0000info\u001f$prefix${it.key}"
    }

    fun getRofiList(): List<String> =
        getMenuOptions()

    protected fun getCommand(backend: Backend): String =
        "echo -e \"${options.joinToString("\n")}\" | ${backend.command} -p '$title'"

    fun main(args: Array<String>) {
        when(args.size) {
            0 -> {
                getMenuOptions().forEach { println(it) }
            }
            else -> {
                when(args[0]) {
                    "-dmenu" -> {
                        execute(Backend.DMENU)
                    }
                    "-rofi-dmenu" -> {
                        execute(Backend.ROFI)
                    }
                    else -> {
                        val selection = args[0].split("/")[0]
                        if (optionMap[selection] != null) {
                            optionMap[selection]!!(args[0])
                        } else {
                            throw IllegalArgumentException("The selected item is not present")
                        }
                    }
                }
            }
        }
    }

    fun execute(backend: Backend) {
        val command = getCommand(backend)
        TODO("Execute command with a backend")
    }

    companion object {
        inline fun menu(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var title = "kmenu"
        var key: String? = null
        var prefix: String = ""
        var options: MutableList<MenuItem> = mutableListOf()

        inline fun option(block: MenuItem.Builder.() -> Unit) {
            val builder = MenuItem.Builder()
            options.add(builder.apply(block).build())
        }

        inline fun submenu(block: Builder.() -> Unit) {
            val submenuBuilder = Builder()
                .apply(block)

            submenuBuilder.prefix = "$prefix${submenuBuilder.key}/"

            val submenu = submenuBuilder.build()

            checkNotNull(submenu.key)
            options.add(MenuItem.Builder().apply {
                label = submenu.title
                key = submenu.key
                action = MenuItem.submenuAction(submenu)
            }.build())
        }

        fun build(): Menu = Menu(this)
    }
}