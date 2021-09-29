package space.dlowl.kmenu.utils

expect fun executeCommand(
    command: String,
    trim: Boolean = true,
    redirectStderr: Boolean = true
): String