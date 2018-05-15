package ru.alfabank.ecomm.dcreator.common

import buf.Buffer
import fs.NodeJS
import kotlinx.coroutines.experimental.await
import kotlin.js.Promise

actual class File {
    val path: String

    actual constructor(parent: File, path: String) {
        this.path = "${parent.path}/$path"
    }

    actual constructor(path: String) {
        this.path = path
    }

    actual fun exists(): Boolean {
        return fs.existsSync(path)
    }
}

actual suspend fun <R> File.withLines(action: suspend (Sequence<String>) -> R): R {
    val promise = Promise<Sequence<String>> { resolve, reject ->
        fs.readFile(this.path) { err: NodeJS.ErrnoException, data: Buffer ->
            resolve(data.toString().split("\n").asSequence())
        }
    }

    return action(promise.await())
}