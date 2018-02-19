package ru.alfabank.ecomm.dcreator.server.utils

import java.time.Duration
import java.time.LocalTime

suspend fun <T> doAndDisplayDiff(message: String = "actions duration in ms:", action: suspend () -> T): T {
    val start = LocalTime.now()
    val result = action()
    val end = LocalTime.now()

    val diff = Duration.between(start, end).toMillis()

    println("$message: $diff ms")

    return result
}