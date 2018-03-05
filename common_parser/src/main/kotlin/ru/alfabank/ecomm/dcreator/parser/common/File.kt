package ru.alfabank.ecomm.dcreator.parser.common

expect class File(parent: File, path: String) {
    fun <R> useLines(action: (Sequence<String>) -> R): R
    fun exists(): Boolean
}