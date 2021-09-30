package space.dlowl.kmenu.menu

import space.dlowl.kmenu.spy.Spy
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MenuTest {
    @Test
    fun testMenuBuilder() {
        val simpleCall = Spy()

        val menu = Menu.menu {
            title = "test kmenu"
            option {
                label = "top level option"
                key = "top"
                action = simpleCall::call
            }
        }

        assertEquals(
            menu.getRofiList(),
            listOf("top level option\\0info\\x1ftop")
        )
        menu.main(arrayOf("top"))
        assertEquals(simpleCall.callCount, 1)

    }
}