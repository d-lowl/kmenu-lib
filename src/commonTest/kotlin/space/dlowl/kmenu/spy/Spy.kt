package space.dlowl.kmenu.spy

class Spy {
    var callCount: Int = 0
    fun call(path: String) {
        callCount += 1
    }
}