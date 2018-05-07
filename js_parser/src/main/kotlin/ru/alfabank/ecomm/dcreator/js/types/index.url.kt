@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("url")
package url

import org.w3c.dom.url.URLSearchParams

external interface UrlObject {
    var auth: String? get() = definedExternally; set(value) = definedExternally
    var hash: String? get() = definedExternally; set(value) = definedExternally
    var host: String? get() = definedExternally; set(value) = definedExternally
    var hostname: String? get() = definedExternally; set(value) = definedExternally
    var href: String? get() = definedExternally; set(value) = definedExternally
    var path: String? get() = definedExternally; set(value) = definedExternally
    var pathname: String? get() = definedExternally; set(value) = definedExternally
    var port: dynamic /* String | Number */ get() = definedExternally; set(value) = definedExternally
    var protocol: String? get() = definedExternally; set(value) = definedExternally
    var query: dynamic /* String | Json */ get() = definedExternally; set(value) = definedExternally
    var search: String? get() = definedExternally; set(value) = definedExternally
    var slashes: Boolean? get() = definedExternally; set(value) = definedExternally
}
external interface Url : UrlObject {
    override var port: String? get() = definedExternally; set(value) = definedExternally
    override var query: Any? get() = definedExternally; set(value) = definedExternally
}
external fun parse(urlStr: String, parseQueryString: Boolean? = definedExternally /* null */, slashesDenoteHost: Boolean? = definedExternally /* null */): Url = definedExternally
external fun format(URL: URL, options: URLFormatOptions? = definedExternally /* null */): String = definedExternally
external fun format(urlObject: String): String = definedExternally
external fun format(urlObject: UrlObject): String = definedExternally
external fun resolve(from: String, to: String): String = definedExternally
external fun domainToASCII(domain: String): String = definedExternally
external fun domainToUnicode(domain: String): String = definedExternally
external interface URLFormatOptions {
    var auth: Boolean? get() = definedExternally; set(value) = definedExternally
    var fragment: Boolean? get() = definedExternally; set(value) = definedExternally
    var search: Boolean? get() = definedExternally; set(value) = definedExternally
    var unicode: Boolean? get() = definedExternally; set(value) = definedExternally
}
external interface `T$16` {
    @nativeGetter
    operator fun get(key: String): dynamic /* String | Array<String> | Nothing? */
    @nativeSetter
    operator fun set(key: String, value: String)
    @nativeSetter
    operator fun set(key: String, value: Array<String>)
    @nativeSetter
    operator fun set(key: String, value: Nothing?)
}
//external open class URLSearchParams : Iterable<Array<String>> {
//    override fun iterator(): Iterator<Array<String>>
//    constructor(init: String? = definedExternally /* null */)
//    constructor(init: Iterable<Array<String>>? = definedExternally /* null */)
//    constructor(init: URLSearchParams? = definedExternally /* null */)
//    constructor(init: `T$16`? = definedExternally /* null */)
//    open fun append(name: String, value: String): Unit = definedExternally
//    open fun delete(name: String): Unit = definedExternally
//    open fun entries(): Iterator<Array<String>> = definedExternally
//    open fun forEach(callback: (value: String, name: String) -> Unit): Unit = definedExternally
//    open fun get(name: String): String? = definedExternally
//    open fun getAll(name: String): Array<String> = definedExternally
//    open fun has(name: String): Boolean = definedExternally
//    open fun keys(): Iterator<String> = definedExternally
//    open fun set(name: String, value: String): Unit = definedExternally
//    open fun sort(): Unit = definedExternally
//    override fun toString(): String = definedExternally
//    open fun values(): Iterator<String> = definedExternally
//}
external open class URL {
    constructor(input: String, base: String? = definedExternally /* null */)
    constructor(input: String, base: URL? = definedExternally /* null */)
    open var hash: String = definedExternally
    open var host: String = definedExternally
    open var hostname: String = definedExternally
    open var href: String = definedExternally
    open var origin: String = definedExternally
    open var password: String = definedExternally
    open var pathname: String = definedExternally
    open var port: String = definedExternally
    open var protocol: String = definedExternally
    open var search: String = definedExternally
    open var searchParams: URLSearchParams = definedExternally
    open var username: String = definedExternally
    override fun toString(): String = definedExternally
    open fun toJSON(): String = definedExternally
}
