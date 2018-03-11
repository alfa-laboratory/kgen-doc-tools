package ru.alfabank.ecomm.dcreator.common

import java.io.File as JFile

actual typealias File = JFile

actual fun <R> File.withLines(action: (Sequence<String>) -> R): R = this.useLines(block = action)