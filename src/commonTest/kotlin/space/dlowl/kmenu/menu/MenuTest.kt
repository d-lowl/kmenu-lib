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
                action = simpleCall::call
            }

            submenu {
                title = "test subkmenu"
                key = "subkmenu"
                option {
                    label = "top level option in the submenu"
                    key = "top"
                    action = submenuCall::call
                }
            }
        }

        assertEquals(
            menu.getRofiList(),
            listOf(
                "top level option\\0info\\x1ftop",
                "test subkmenu\\0info\\x1fsubkmenu",
            )
        )
        menu.main(arrayOf("top"))
        assertEquals(simpleCall.callCount, 1)

        menu.main(arrayOf("subkmenu/top"))
        assertEquals(submenuCall.callCount, 1)

    }
}