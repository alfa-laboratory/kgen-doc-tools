package ru.alfabank.ecomm.dcreator.common

expect class File {
    constructor(parent: File, path: String)

    constructor(path: String)

    fun exists(): Boolean
}

expect fun  <R> File.withLines(action: (Sequence<String>) -> R): R