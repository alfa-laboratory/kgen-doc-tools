package ru.alfabank.ecomm.dcreator.common

expect class File {
    constructor(parent: File, path: String)

    constructor(path: String)

    fun exists(): Boolean
}

expect suspend fun <R> File.withLines(action: suspend (Sequence<String>) -> R): R