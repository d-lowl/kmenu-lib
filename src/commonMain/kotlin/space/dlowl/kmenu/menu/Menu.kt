package space.dlowl.kmenu.menu

data class Menu(val title: String, val options: List<MenuItem>? = null) {

    private constructor(builder: Builder) : this(builder.title, builder.options)

    fun getMenuOptions(): List<String> = options?.map { it.label } ?: listOf()

    fun getCommand(backend: Backend): String = when(options) {
        null -> "echo -e \"\" | ${backend.command} -p '$title'"
        else -> "echo -e \"${options.joinToString("\n")}\" | ${backend.command} -p '$title'"
    }

    companion object {
        inline fun menu(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var title = "kmenu"
        var options: MutableList<MenuItem>? = null

        inline fun option(block: MenuItem.Builder.() -> Unit) {
            val builder = MenuItem.Builder()
            if (options == null) options = mutableListOf()
            options!!.add(builder.apply(block).build())
        }

        fun build(): Menu = Menu(this)
    }
}