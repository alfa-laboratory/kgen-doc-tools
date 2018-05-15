package ru.alfabank.ecomm.dcreator.common

import java.io.File as JFile

actual typealias File = JFile

actual suspend fun <R> File.withLines(action: suspend (Sequence<String>) -> R): R {
    return this.useLines(block = {
        kotlinx.coroutines.experimental.runBlocking { action(it) }
    })
}