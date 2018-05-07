@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("http")
package http

import buf.Buffer

external interface IncomingHttpHeaders {
    @nativeGetter
    operator fun get(header: String): dynamic /* String | Array<String> | Nothing? */
    @nativeSetter
    operator fun set(header: String, value: String)
    @nativeSetter
    operator fun set(header: String, value: Array<String>)
    @nativeSetter
    operator fun set(header: String, value: Nothing?)
}
external interface OutgoingHttpHeaders {
    @nativeGetter
    operator fun get(header: String): dynamic /* String | Number | Array<String> | Nothing? */
    @nativeSetter
    operator fun set(header: String, value: String)
    @nativeSetter
    operator fun set(header: String, value: Number)
    @nativeSetter
    operator fun set(header: String, value: Array<String>)
    @nativeSetter
    operator fun set(header: String, value: Nothing?)
}
external interface RequestOptions {
    var protocol: String? get() = definedExternally; set(value) = definedExternally
    var host: String? get() = definedExternally; set(value) = definedExternally
    var hostname: String? get() = definedExternally; set(value) = definedExternally
    var family: Number? get() = definedExternally; set(value) = definedExternally
    var port: dynamic /* String | Number */ get() = definedExternally; set(value) = definedExternally
    var localAddress: String? get() = definedExternally; set(value) = definedExternally
    var socketPath: String? get() = definedExternally; set(value) = definedExternally
    var method: String? get() = definedExternally; set(value) = definedExternally
    var path: String? get() = definedExternally; set(value) = definedExternally
    var headers: OutgoingHttpHeaders? get() = definedExternally; set(value) = definedExternally
    var auth: String? get() = definedExternally; set(value) = definedExternally
    var agent: dynamic /* Boolean | Agent */ get() = definedExternally; set(value) = definedExternally
    var timeout: Number? get() = definedExternally; set(value) = definedExternally
}
external interface Server : net.Server {
    fun setTimeout(msecs: Number, callback: Function<*>)
    var maxHeadersCount: Number
    var timeout: Number
    override var listening: Boolean
}
external interface ServerRequest : IncomingMessage {
    override var connection: dynamic
}
external interface ServerResponse : stream.internal.Writable {
    fun write(buffer: Buffer): Boolean
    override fun write(buffer: Buffer, cb: Function<*>?): Boolean
    override fun write(str: String, cb: Function<*>?): Boolean
    override fun write(str: String, encoding: String?, cb: Function<*>?): Boolean
    fun write(str: String, encoding: String? = definedExternally /* null */, fd: String? = definedExternally /* null */): Boolean
    fun writeContinue()
    fun writeHead(statusCode: Number, reasonPhrase: String? = definedExternally /* null */, headers: OutgoingHttpHeaders? = definedExternally /* null */)
    fun writeHead(statusCode: Number, headers: OutgoingHttpHeaders? = definedExternally /* null */)
    var statusCode: Number
    var statusMessage: String
    var headersSent: Boolean
    fun setHeader(name: String, value: String)
    fun setHeader(name: String, value: Array<String>)
    fun setHeader(name: String, value: Nothing?)
    fun setTimeout(msecs: Number, callback: Function<*>): ServerResponse
    var sendDate: Boolean
    fun getHeader(name: String): String
    fun removeHeader(name: String)
    fun write(chunk: Any, encoding: String? = definedExternally /* null */): Any
    fun addTrailers(headers: OutgoingHttpHeaders)
    var finished: Boolean
    fun end()
    override fun end(buffer: Buffer, cb: Function<*>?)
    override fun end(str: String, cb: Function<*>?)
    override fun end(str: String, encoding: String?, cb: Function<*>?)
    fun end(data: Any? = definedExternally /* null */, encoding: String? = definedExternally /* null */)
    fun write(str: String): Boolean
    fun writeHead(statusCode: Number)
    override fun end(str: String)
}
external interface ClientRequest : stream.internal.Writable {
    fun write(buffer: Buffer): Boolean
    override fun write(buffer: Buffer, cb: Function<*>?): Boolean
    override fun write(str: String, cb: Function<*>?): Boolean
    override fun write(str: String, encoding: String?, cb: Function<*>?): Boolean
    fun write(str: String, encoding: String? = definedExternally /* null */, fd: String? = definedExternally /* null */): Boolean
    fun write(chunk: Any, encoding: String? = definedExternally /* null */)
    fun abort()
    fun setTimeout(timeout: Number, callback: Function<*>? = definedExternally /* null */)
    fun setNoDelay(noDelay: Boolean? = definedExternally /* null */)
    fun setSocketKeepAlive(enable: Boolean? = definedExternally /* null */, initialDelay: Number? = definedExternally /* null */)
    fun setHeader(name: String, value: String)
    fun setHeader(name: String, value: Array<String>)
    fun setHeader(name: String, value: Nothing?)
    fun getHeader(name: String): String
    fun removeHeader(name: String)
    fun addTrailers(headers: OutgoingHttpHeaders)
    fun end()
    override fun end(buffer: Buffer, cb: Function<*>?)
    override fun end(str: String, cb: Function<*>?)
    override fun end(str: String, encoding: String?, cb: Function<*>?)
    fun end(data: Any? = definedExternally /* null */, encoding: String? = definedExternally /* null */)
    fun write(str: String): Boolean
    override fun end(str: String)
}
external interface IncomingMessage : stream.internal.Readable {
    var httpVersion: String
    var httpVersionMajor: Number
    var httpVersionMinor: Number
    var connection: dynamic
    var headers: IncomingHttpHeaders
    var rawHeaders: Array<String>
    var trailers: OutgoingHttpHeaders
    var rawTrailers: Array<String>
    fun setTimeout(msecs: Number, callback: Function<*>): NodeJS.Timer
    var method: String? get() = definedExternally; set(value) = definedExternally
    var url: String? get() = definedExternally; set(value) = definedExternally
    var statusCode: Number? get() = definedExternally; set(value) = definedExternally
    var statusMessage: String? get() = definedExternally; set(value) = definedExternally
    var socket: dynamic
    fun destroy(error: Error? = definedExternally /* null */)
}
external interface ClientResponse : IncomingMessage
external interface AgentOptions {
    var keepAlive: Boolean? get() = definedExternally; set(value) = definedExternally
    var keepAliveMsecs: Number? get() = definedExternally; set(value) = definedExternally
    var maxSockets: Number? get() = definedExternally; set(value) = definedExternally
    var maxFreeSockets: Number? get() = definedExternally; set(value) = definedExternally
}
external open class Agent(opts: AgentOptions? = definedExternally /* null */) {
    open var maxSockets: Number = definedExternally
    open var sockets: Any = definedExternally
    open var requests: Any = definedExternally
    open fun destroy(): Unit = definedExternally
}
external var METHODS: Array<String> = definedExternally
external object STATUS_CODES {
    @nativeGetter
    operator fun get(errorCode: Number): String?
    @nativeSetter
    operator fun set(errorCode: Number, value: String)
    @nativeGetter
    operator fun get(errorCode: String): String?
    @nativeSetter
    operator fun set(errorCode: String, value: String)
}
external fun createServer(requestListener: ((request: IncomingMessage, response: ServerResponse) -> Unit)? = definedExternally /* null */): Server = definedExternally
external fun createClient(port: Number? = definedExternally /* null */, host: String? = definedExternally /* null */): Any = definedExternally
external fun request(options: String, callback: ((res: IncomingMessage) -> Unit)? = definedExternally /* null */): ClientRequest = definedExternally
external fun request(options: RequestOptions, callback: ((res: IncomingMessage) -> Unit)? = definedExternally /* null */): ClientRequest = definedExternally
external fun get(options: Any, callback: ((res: IncomingMessage) -> Unit)? = definedExternally /* null */): ClientRequest = definedExternally
external var globalAgent: Agent = definedExternally
