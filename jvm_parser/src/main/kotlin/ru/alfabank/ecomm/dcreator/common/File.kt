package ru.alfabank.ecomm.dcreator.common

import java.io.File as JFile

actual class File {
    private val file: JFile

    actual fun toPath(): String = file.absolutePath

    actual fun <R> useLines(action: (Sequence<String>) -> R): R {
        return file.useLines(block = action)
    }

    actual fun exists(): Boolean = file.exists()

    actual constructor(parent: File, path: String) {
        file = JFile(parent.toPath(), path)
    }

    actual constructor(path: String) {
        file = JFile(path)
    }
}