package space.dlowl.kmenu.spy

class Spy {
    var callCount: Int = 0
    fun call() {
        callCount += 1
    }
}