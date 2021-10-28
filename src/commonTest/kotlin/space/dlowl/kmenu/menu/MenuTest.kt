package space.dlowl.kmenu.menu

import space.dlowl.kmenu.spy.Spy
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MenuTest {
    @Test
    fun testMenuBuilder() {
        val simpleCall = Spy()
        val submenuCall = Spy()

        val menu = Menu.menu {
            title = "test kmenu"
            option {
                label = "top level option"
                key = "top"
                func = simpleCall::call
            }

            submenu {
                title = "test subkmenu"
                key = "subkmenu"
                option {
                    label = "top level option in the submenu"
                    key = "top"
                    func = submenuCall::call
                }
            }
        }

        assertEquals(menu.title, "test kmenu")
        assertEquals(menu.getSubmenu("subkmenu").title, "test subkmenu")
        assertEquals(menu.optionMap["top"]?.func, simpleCall::call)


        assertEquals(
            menu.getMenuOptions(listOf()),
            listOf(
                "top level option\u0000info\u001F[{\"type\":\"ENTRY\",\"value\":\"top\"}]\u001fmeta\u001f\u001fnonselectable\u001ffalse",
                "test subkmenu\u0000info\u001F[{\"type\":\"SUBMENU\",\"value\":\"subkmenu\"}]\u001fmeta\u001f\u001fnonselectable\u001ffalse",
            )
        )

        val prefix = listOf(menu.optionMap["subkmenu"]!!.selection)
        val submenu = menu.getSubmenu("subkmenu")

        assertEquals(submenu.title, "test subkmenu")
        assertEquals(submenu.optionMap["top"]?.func, submenuCall::call)

        assertEquals(
            submenu.getMenuOptions(prefix),
            listOf(
                "top level option in the submenu\u0000info\u001F[{\"type\":\"SUBMENU\",\"value\":\"subkmenu\"},{\"type\":\"ENTRY\",\"value\":\"top\"}]\u001fmeta\u001f\u001fnonselectable\u001ffalse",
            )
        )
    }
}