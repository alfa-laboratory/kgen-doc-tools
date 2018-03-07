package ru.alfabank.ecomm.dcreator.common

expect class File {
    constructor(parent: File, path: String)

    constructor(path: String)

    fun toPath(): String
    fun <R> useLines(action: (Sequence<String>) -> R): R
    fun exists(): Boolean
}