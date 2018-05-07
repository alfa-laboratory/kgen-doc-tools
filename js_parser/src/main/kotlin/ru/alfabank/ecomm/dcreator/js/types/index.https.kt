@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("https")
package https

external class tls {
    class SecureContext
}

external interface ServerOptions {
    var pfx: Any? get() = definedExternally; set(value) = definedExternally
    var key: Any? get() = definedExternally; set(value) = definedExternally
    var passphrase: String? get() = definedExternally; set(value) = definedExternally
    var cert: Any? get() = definedExternally; set(value) = definedExternally
    var ca: Any? get() = definedExternally; set(value) = definedExternally
    var crl: Any? get() = definedExternally; set(value) = definedExternally
    var ciphers: String? get() = definedExternally; set(value) = definedExternally
    var honorCipherOrder: Boolean? get() = definedExternally; set(value) = definedExternally
    var requestCert: Boolean? get() = definedExternally; set(value) = definedExternally
    var rejectUnauthorized: Boolean? get() = definedExternally; set(value) = definedExternally
    var NPNProtocols: Any? get() = definedExternally; set(value) = definedExternally
    var SNICallback: ((servername: String, cb: (err: Error, ctx: tls.SecureContext) -> Any) -> Any)? get() = definedExternally; set(value) = definedExternally
}
external interface RequestOptions : http.RequestOptions {
    var pfx: Any? get() = definedExternally; set(value) = definedExternally
    var key: Any? get() = definedExternally; set(value) = definedExternally
    var passphrase: String? get() = definedExternally; set(value) = definedExternally
    var cert: Any? get() = definedExternally; set(value) = definedExternally
    var ca: Any? get() = definedExternally; set(value) = definedExternally
    var ciphers: String? get() = definedExternally; set(value) = definedExternally
    var rejectUnauthorized: Boolean? get() = definedExternally; set(value) = definedExternally
    var secureProtocol: String? get() = definedExternally; set(value) = definedExternally
}
external interface Agent : http.Agent {
    var options: AgentOptions? get() = definedExternally; set(value) = definedExternally
    companion object {
    }
}
external interface AgentOptions : http.AgentOptions {
    var pfx: Any? get() = definedExternally; set(value) = definedExternally
    var key: Any? get() = definedExternally; set(value) = definedExternally
    var passphrase: String? get() = definedExternally; set(value) = definedExternally
    var cert: Any? get() = definedExternally; set(value) = definedExternally
    var ca: Any? get() = definedExternally; set(value) = definedExternally
    var ciphers: String? get() = definedExternally; set(value) = definedExternally
    var rejectUnauthorized: Boolean? get() = definedExternally; set(value) = definedExternally
    var secureProtocol: String? get() = definedExternally; set(value) = definedExternally
    var maxCachedSessions: Number? get() = definedExternally; set(value) = definedExternally
}
external interface Server
external fun createServer(options: ServerOptions, requestListener: Function<*>? = definedExternally /* null */): Server = definedExternally
external fun request(options: String, callback: ((res: http.IncomingMessage) -> Unit)? = definedExternally /* null */): http.ClientRequest = definedExternally
external fun request(options: RequestOptions, callback: ((res: http.IncomingMessage) -> Unit)? = definedExternally /* null */): http.ClientRequest = definedExternally
external fun get(options: RequestOptions, callback: ((res: http.IncomingMessage) -> Unit)? = definedExternally /* null */): http.ClientRequest = definedExternally
external var globalAgent: dynamic = definedExternally
