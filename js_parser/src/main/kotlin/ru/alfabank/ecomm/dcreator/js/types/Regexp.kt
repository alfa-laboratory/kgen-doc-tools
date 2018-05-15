package ru.alfabank.ecomm.dcreator.js.types

@JsModule("named-js-regexp")
external fun namedRegexp(regex: String, vararg flags: String? = definedExternally): Regexp

external interface Regexp {
    fun exec(value: String): RegexpResult?
    fun test(value: String): Boolean
}

external class RegexpResult {
    fun group(groupName: String): String?
}