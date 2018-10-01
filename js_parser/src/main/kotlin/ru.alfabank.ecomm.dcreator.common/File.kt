package ru.alfabank.ecomm.dcreator.common

import buf.Buffer
import fs.NodeJS
import kotlinx.coroutines.await
import kotlin.js.Promise

actual class File {
    val rawPath: String

    actual constructor(parent: File, path: String) {
        this.rawPath = "${parent.rawPath}/$path"
    }

    actual constructor(path: String) {
        this.rawPath = path
    }

    actual fun exists(): Boolean {
        return fs.existsSync(rawPath)
    }

    actual fun getParentFile(): File {
        return File(path.parse(rawPath).dir)
    }

    actual fun getAbsolutePath(): String {
        return path.resolve(rawPath)
    }
}

actual suspend fun <R> File.withLines(action: suspend (Sequence<String>) -> R): R {
    val promise = Promise<Sequence<String>> { resolve, reject ->
        fs.readFile(this.rawPath) { err: NodeJS.ErrnoException, data: Buffer ->
            resolve(data.toString().split("\n").asSequence())
        }
    }

    return action(promise.await())
}