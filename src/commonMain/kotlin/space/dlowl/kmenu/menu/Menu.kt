package space.dlowl.kmenu.menu

import space.dlowl.kmenu.Executor
import space.dlowl.kmenu.rofi.RofiInput
import space.dlowl.kmenu.rofi.RofiSelection
import space.dlowl.kmenu.rofi.getRofiInput

data class Menu(
    val title: String,
    val key: String? = null,
    val options: List<MenuItem>,
    val prefix: String = "",
) {
    var optionMap: Map<String, MenuItem> = options.map { it.key to it }.toMap()
    private constructor(builder: Builder) : this(builder.title, builder.key, builder.options)

    fun getMenuOptions(prefix: List<RofiSelection>): List<String> = options.map { it.getRofiString(prefix) }

    fun getSubmenu(key: String) = optionMap[key]!!.navigate()

    fun executeEntry(key: String, rofiInput: RofiInput) = optionMap[key]!!.execute(rofiInput)

    protected fun getCommand(backend: Backend): String =
        "echo -e \"${options.joinToString("\n")}\" | ${backend.command} -p '$title'"

    fun main(args: Array<String>) {
        when(if (args.isEmpty()) "" else args[0]) {
            "-dmenu" -> {
                executeLegacy(Backend.DMENU)
            }
            "-rofi-dmenu" -> {
                executeLegacy(Backend.ROFI)
            }
            else -> {
                val input = getRofiInput()
                Executor.execute(this, input)
            }
        }
    }

    fun executeLegacy(backend: Backend) {
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

            val _submenu = submenuBuilder.build()

            checkNotNull(_submenu.key)
            options.add(MenuItem.Builder().apply {
                label = _submenu.title
                key = _submenu.key
                submenu = _submenu
            }.build())
        }

        fun build(): Menu = Menu(this)
    }
}