package ru.alfabank.ecomm.dcreator.common

import ru.alfabank.ecomm.dcreator.js.types.UUID as JUUID

actual class UUID(private val uuidValue: String) {
    actual fun print(): String = uuidValue

    actual companion object {
        actual fun randomUUID(): UUID = UUID(JUUID.v4())
    }
}