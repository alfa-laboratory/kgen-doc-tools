package ru.alfabank.ecomm.dcreator.parser.common

actual class File actual constructor(parent: File, path: String) {
    private val file = java.io.File(parent.toPath(), path)

    actual fun toPath(): String = file.absolutePath

    actual fun <R> useLines(action: (Sequence<String>) -> R): R {
        return file.useLines(block = action)
    }

    actual fun exists(): Boolean = file.exists()
}