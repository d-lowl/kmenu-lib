package space.dlowl.kmenu.menu

import kotlin.test.Test
import kotlin.test.assertEquals

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
        assertEquals(Menu("test menu", listOf()).getMenuOptions(), listOf())
        assertEquals(Menu("test menu", listOf(MenuItem("item1"))).getMenuOptions(), listOf("item1"))
        assertEquals(Menu("test menu", listOf(MenuItem("item1"), MenuItem("item2"))).getMenuOptions(), listOf("item1", "item2"))
    }

    @Test
    fun getCommand() {
    }
}