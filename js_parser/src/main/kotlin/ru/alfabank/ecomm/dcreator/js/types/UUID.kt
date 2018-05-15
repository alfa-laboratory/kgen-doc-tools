package ru.alfabank.ecomm.dcreator.js.types

@JsModule("node-uuid")
external object UUID {
    fun v4(): String
}