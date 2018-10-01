package ru.alfabank.ecomm.dcreator.common

import kotlinx.coroutines.runBlocking
import java.io.File as JFile

actual typealias File = JFile

actual suspend fun <R> File.withLines(action: suspend (Sequence<String>) -> R): R {
    return this.useLines(block = {
        runBlocking { action(it) }
    })
}