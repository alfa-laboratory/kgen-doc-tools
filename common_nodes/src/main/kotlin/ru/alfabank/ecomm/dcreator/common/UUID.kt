package ru.alfabank.ecomm.dcreator.common

expect class UUID {
    fun print(): String

    companion object {
        fun randomUUID(): UUID
    }
}