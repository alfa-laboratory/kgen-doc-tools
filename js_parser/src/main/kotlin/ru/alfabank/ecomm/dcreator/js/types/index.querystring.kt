@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("querystring")
package querystring

external interface StringifyOptions {
    var encodeURIComponent: Function<*>? get() = definedExternally; set(value) = definedExternally
}
external interface ParseOptions {
    var maxKeys: Number? get() = definedExternally; set(value) = definedExternally
    var decodeURIComponent: Function<*>? get() = definedExternally; set(value) = definedExternally
}
external fun <T> stringify(obj: T, sep: String? = definedExternally /* null */, eq: String? = definedExternally /* null */, options: StringifyOptions? = definedExternally /* null */): String = definedExternally
external fun parse(str: String, sep: String? = definedExternally /* null */, eq: String? = definedExternally /* null */, options: ParseOptions? = definedExternally /* null */): Any = definedExternally
external fun <T : Any> parse(str: String, sep: String? = definedExternally /* null */, eq: String? = definedExternally /* null */, options: ParseOptions? = definedExternally /* null */): T = definedExternally
external fun escape(str: String): String = definedExternally
external fun unescape(str: String): String = definedExternally
