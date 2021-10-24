package space.dlowl.kmenu.rofi

import kotlinx.serialization.Serializable

@Serializable
data class RofiSelection(val type: RofiSelectionType, val value: String)
