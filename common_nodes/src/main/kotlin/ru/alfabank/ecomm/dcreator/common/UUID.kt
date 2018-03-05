package ru.alfabank.ecomm.dcreator.common

expect class UUID {
    override fun toString(): String

    companion object {
        fun randomUUID(): UUID
    }
}