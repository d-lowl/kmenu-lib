package space.dlowl.kmenu.menu

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MenuTest {

    @Test
    fun testMenuBuilder() {
        Menu.menu {
            title = "test kmenu"
            option {
                label = "option 1"
            }
            option {
                label = "option 2"
                action = { "yey" }
            }
        }
    }

    @Test
    fun getMenuOptions() {
        assert(Menu("test menu").getMenuOptions() == listOf<String>())
        assert(Menu("test menu", listOf()).getMenuOptions() == listOf<String>())
        assert(Menu("test menu", listOf(MenuItem("item1"))).getMenuOptions() == listOf("item1"))
        assert(Menu("test menu", listOf(MenuItem("item1"), MenuItem("item2"))).getMenuOptions() == listOf("item1", "item2"))
    }

    @Test
    fun getCommand() {
    }
}