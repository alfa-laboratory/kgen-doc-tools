@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("punycode")
package punycode

external fun decode(string: String): String = definedExternally
external fun encode(string: String): String = definedExternally
external fun toUnicode(domain: String): String = definedExternally
external fun toASCII(domain: String): String = definedExternally
external interface ucs2 {
    fun decode(string: String): Array<Number>
    fun encode(codePoints: Array<Number>): String
}
external var version: Any = definedExternally
