package ru.alfabank.ecomm.dcreator.common

import java.util.UUID as JUUID

actual class UUID(private val uuid: JUUID) {
    actual fun print(): String = uuid.toString()

    actual companion object {
        actual fun randomUUID(): UUID = UUID(JUUID.randomUUID())
    }
}