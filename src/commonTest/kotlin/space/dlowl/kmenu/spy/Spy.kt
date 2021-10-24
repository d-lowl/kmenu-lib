package space.dlowl.kmenu.spy

import space.dlowl.kmenu.rofi.RofiInput

class Spy {
    var callCount: Int = 0
    fun call(input: RofiInput) {
        callCount += 1
    }
}