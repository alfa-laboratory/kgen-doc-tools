@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("net")

package net

import buf.Buffer

external interface `T$17` {
    var port: Number
    var family: String
    var address: String
}

external interface Socket {
    fun write(buffer: Buffer): Boolean
    fun write(buffer: Buffer, cb: Function<*>?): Boolean
    fun write(str: String, cb: Function<*>?): Boolean
    fun write(str: String, encoding: String?, cb: Function<*>?): Boolean
    fun write(str: String, encoding: String? = definedExternally /* null */, fd: String? = definedExternally /* null */): Boolean
    fun connect(port: Number, host: String? = definedExternally /* null */, connectionListener: Function<*>? = definedExternally /* null */)
    fun connect(path: String, connectionListener: Function<*>? = definedExternally /* null */)
    var bufferSize: Number
    fun setEncoding(encoding: String?): dynamic /* this */
    fun write(data: Any, encoding: String?, callback: Function<*>?)
    fun destroy(err: Any? = definedExternally /* null */)
    fun pause(): dynamic /* this */
    fun resume(): dynamic /* this */
    fun setTimeout(timeout: Number, callback: Function<*>? = definedExternally /* null */): dynamic /* this */
    fun setNoDelay(noDelay: Boolean? = definedExternally /* null */): dynamic /* this */
    fun setKeepAlive(enable: Boolean? = definedExternally /* null */, initialDelay: Number? = definedExternally /* null */): dynamic /* this */
    fun address(): `T$17`
    fun unref()
    fun ref()
    var remoteAddress: String
    var remoteFamily: String
    var remotePort: Number
    var localAddress: String
    var localPort: Number
    var bytesRead: Number
    var bytesWritten: Number
    var connecting: Boolean
    var destroyed: Boolean
    fun end()
    fun end(buffer: Buffer, cb: Function<*>?)
    fun end(str: String, cb: Function<*>?)
    fun end(str: String, encoding: String?, cb: Function<*>?)
    fun end(data: Any? = definedExternally /* null */, encoding: String? = definedExternally /* null */)
    fun addListener(event: String, listener: Function<*>): dynamic /* this */
    fun addListener(event: String /* "close" */, listener: (had_error: Boolean) -> Unit): dynamic /* this */
    fun addListener(event: String /* "connect" */, listener: () -> Unit): dynamic /* this */
    fun addListener(event: String /* "data" */, listener: (data: Buffer) -> Unit): dynamic /* this */
    fun addListener(event: String /* "drain" */, listener: () -> Unit): dynamic /* this */
    fun addListener(event: String /* "end" */, listener: () -> Unit): dynamic /* this */
    fun addListener(event: String /* "error" */, listener: (err: Error) -> Unit): dynamic /* this */
    fun addListener(event: String /* "timeout" */, listener: () -> Unit): dynamic /* this */
    fun emit(event: String, vararg args: Any): Boolean
    fun emit(event: Any?, vararg args: Any): Boolean
    fun emit(event: String /* "close" */, had_error: Boolean): Boolean
    fun emit(event: String /* "connect" */): Boolean
    fun emit(event: String /* "data" */, data: Buffer): Boolean
    fun emit(event: String /* "drain" */): Boolean
    fun emit(event: String /* "end" */): Boolean
    fun emit(event: String /* "error" */, err: Error): Boolean
    fun emit(event: String /* "lookup" */, err: Error, address: String, family: String, host: String): Boolean
    fun emit(event: String /* "lookup" */, err: Error, address: String, family: Number, host: String): Boolean
    fun emit(event: String /* "timeout" */): Boolean
    fun on(event: String, listener: Function<*>): dynamic /* this */
    fun on(event: String /* "close" */, listener: (had_error: Boolean) -> Unit): dynamic /* this */
    fun on(event: String /* "connect" */, listener: () -> Unit): dynamic /* this */
    fun on(event: String /* "data" */, listener: (data: Buffer) -> Unit): dynamic /* this */
    fun on(event: String /* "drain" */, listener: () -> Unit): dynamic /* this */
    fun on(event: String /* "end" */, listener: () -> Unit): dynamic /* this */
    fun on(event: String /* "error" */, listener: (err: Error) -> Unit): dynamic /* this */
    fun on(event: String /* "lookup" */, listener: (err: Error, address: String, family: dynamic /* String | Number */, host: String) -> Unit): dynamic /* this */
    fun on(event: String /* "timeout" */, listener: () -> Unit): dynamic /* this */
    fun once(event: String, listener: Function<*>): dynamic /* this */
    //    fun once(event: String /* "close" */, listener: (had_error: Boolean) -> Unit): dynamic /* this */
//    fun once(event: String /* "connect" */, listener: () -> Unit): dynamic /* this */
//    fun once(event: String /* "data" */, listener: (data: Buffer) -> Unit): dynamic /* this */
//    fun once(event: String /* "drain" */, listener: () -> Unit): dynamic /* this */
//    fun once(event: String /* "end" */, listener: () -> Unit): dynamic /* this */
//    fun once(event: String /* "error" */, listener: (err: Error) -> Unit): dynamic /* this */
//    fun once(event: String /* "lookup" */, listener: (err: Error, address: String, family: dynamic /* String | Number */, host: String) -> Unit): dynamic /* this */
//    fun once(event: String /* "timeout" */, listener: () -> Unit): dynamic /* this */
//    override fun prependListener(event: String, listener: Function<*>): dynamic /* this */
//    fun prependListener(event: String /* "close" */, listener: (had_error: Boolean) -> Unit): dynamic /* this */
//    fun prependListener(event: String /* "connect" */, listener: () -> Unit): dynamic /* this */
//    fun prependListener(event: String /* "data" */, listener: (data: Buffer) -> Unit): dynamic /* this */
//    fun prependListener(event: String /* "drain" */, listener: () -> Unit): dynamic /* this */
//    fun prependListener(event: String /* "end" */, listener: () -> Unit): dynamic /* this */
//    fun prependListener(event: String /* "error" */, listener: (err: Error) -> Unit): dynamic /* this */
//    fun prependListener(event: String /* "lookup" */, listener: (err: Error, address: String, family: dynamic /* String | Number */, host: String) -> Unit): dynamic /* this */
//    fun prependListener(event: String /* "timeout" */, listener: () -> Unit): dynamic /* this */
//    override fun prependOnceListener(event: String, listener: Function<*>): dynamic /* this */
//    fun prependOnceListener(event: String /* "close" */, listener: (had_error: Boolean) -> Unit): dynamic /* this */
//    fun prependOnceListener(event: String /* "connect" */, listener: () -> Unit): dynamic /* this */
//    fun prependOnceListener(event: String /* "data" */, listener: (data: Buffer) -> Unit): dynamic /* this */
//    fun prependOnceListener(event: String /* "drain" */, listener: () -> Unit): dynamic /* this */
//    fun prependOnceListener(event: String /* "end" */, listener: () -> Unit): dynamic /* this */
//    fun prependOnceListener(event: String /* "error" */, listener: (err: Error) -> Unit): dynamic /* this */
//    fun prependOnceListener(event: String /* "lookup" */, listener: (err: Error, address: String, family: dynamic /* String | Number */, host: String) -> Unit): dynamic /* this */
//    fun prependOnceListener(event: String /* "timeout" */, listener: () -> Unit): dynamic /* this */
    fun write(str: String): Boolean

    fun end(str: String)
}

external interface ListenOptions {
    var port: Number? get() = definedExternally; set(value) = definedExternally
    var host: String? get() = definedExternally; set(value) = definedExternally
    var backlog: Number? get() = definedExternally; set(value) = definedExternally
    var path: String? get() = definedExternally; set(value) = definedExternally
    var exclusive: Boolean? get() = definedExternally; set(value) = definedExternally
}

external interface Server : events.internal.EventEmitter {
    fun listen(port: Number, hostname: String? = definedExternally /* null */, backlog: Number? = definedExternally /* null */, listeningListener: Function<*>? = definedExternally /* null */): Server
    fun listen(port: Number, hostname: String? = definedExternally /* null */, listeningListener: Function<*>? = definedExternally /* null */): Server
    fun listen(port: Number, backlog: Number? = definedExternally /* null */, listeningListener: Function<*>? = definedExternally /* null */): Server
    fun listen(port: Number, listeningListener: Function<*>? = definedExternally /* null */): Server
    fun listen(path: String, backlog: Number? = definedExternally /* null */, listeningListener: Function<*>? = definedExternally /* null */): Server
    fun listen(path: String, listeningListener: Function<*>? = definedExternally /* null */): Server
    fun listen(options: ListenOptions, listeningListener: Function<*>? = definedExternally /* null */): Server
    fun listen(handle: Any, backlog: Number? = definedExternally /* null */, listeningListener: Function<*>? = definedExternally /* null */): Server
    fun listen(handle: Any, listeningListener: Function<*>? = definedExternally /* null */): Server
    fun close(callback: Function<*>? = definedExternally /* null */): Server
    fun address(): `T$17`
    fun getConnections(cb: (error: Error, count: Number) -> Unit)
    fun ref(): Server
    fun unref(): Server
    var maxConnections: Number
    var connections: Number
    var listening: Boolean
    override fun addListener(event: String, listener: Function<*>): Server /* this */
    fun addListener(event: String /* "close" */, listener: () -> Unit): Server /* this */
    fun addListener(event: String /* "connection" */, listener: (socket: dynamic) -> Unit): Server /* this */
    fun addListener(event: String /* "error" */, listener: (err: Error) -> Unit): Server /* this */
    fun addListener(event: String /* "listening" */, listener: () -> Unit): Server /* this */
    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any?, vararg args: Any): Boolean
    fun emit(event: String /* "close" */): Boolean
    override fun emit(event: String /* "connection" */, socket: dynamic): Boolean
    fun emit(event: String /* "error" */, err: Error): Boolean
    fun emit(event: String /* "listening" */): Boolean
    override fun on(event: String, listener: Function<*>): Server /* this */
    fun on(event: String /* "close" */, listener: () -> Unit): Server /* this */
    fun on(event: String /* "connection" */, listener: (socket: dynamic) -> Unit): Server /* this */
    fun on(event: String /* "error" */, listener: (err: Error) -> Unit): Server /* this */
    fun on(event: String /* "listening" */, listener: () -> Unit): Server /* this */
    override fun once(event: String, listener: Function<*>): Server /* this */
    fun once(event: String /* "close" */, listener: () -> Unit): Server /* this */
    fun once(event: String /* "connection" */, listener: (socket: dynamic) -> Unit): Server /* this */
    fun once(event: String /* "error" */, listener: (err: Error) -> Unit): Server /* this */
    fun once(event: String /* "listening" */, listener: () -> Unit): Server /* this */
    override fun prependListener(event: String, listener: Function<*>): Server /* this */
    fun prependListener(event: String /* "close" */, listener: () -> Unit): Server /* this */
    fun prependListener(event: String /* "connection" */, listener: (socket: dynamic) -> Unit): Server /* this */
    fun prependListener(event: String /* "error" */, listener: (err: Error) -> Unit): Server /* this */
    fun prependListener(event: String /* "listening" */, listener: () -> Unit): Server /* this */
    override fun prependOnceListener(event: String, listener: Function<*>): Server /* this */
    fun prependOnceListener(event: String /* "close" */, listener: () -> Unit): Server /* this */
    fun prependOnceListener(event: String /* "connection" */, listener: (socket: dynamic) -> Unit): Server /* this */
    fun prependOnceListener(event: String /* "error" */, listener: (err: Error) -> Unit): Server /* this */
    fun prependOnceListener(event: String /* "listening" */, listener: () -> Unit): Server /* this */
    fun listen(port: Number): Server
    fun listen(path: String): Server
    fun listen(handle: Any): Server
}

external fun createServer(connectionListener: ((socket: dynamic) -> Unit)? = definedExternally /* null */): Server = definedExternally
external interface `T$18` {
    var allowHalfOpen: Boolean? get() = definedExternally; set(value) = definedExternally
    var pauseOnConnect: Boolean? get() = definedExternally; set(value) = definedExternally
}

external fun createServer(options: `T$18`? = definedExternally /* null */, connectionListener: ((socket: dynamic) -> Unit)? = definedExternally /* null */): Server = definedExternally
external interface `T$19` {
    var port: Number
    var host: String? get() = definedExternally; set(value) = definedExternally
    var localAddress: String? get() = definedExternally; set(value) = definedExternally
    var localPort: String? get() = definedExternally; set(value) = definedExternally
    var family: Number? get() = definedExternally; set(value) = definedExternally
    var allowHalfOpen: Boolean? get() = definedExternally; set(value) = definedExternally
}

external fun connect(options: `T$19`, connectionListener: Function<*>? = definedExternally /* null */): dynamic = definedExternally
external fun connect(port: Number, host: String? = definedExternally /* null */, connectionListener: Function<*>? = definedExternally /* null */): dynamic = definedExternally
external fun connect(path: String, connectionListener: Function<*>? = definedExternally /* null */): dynamic = definedExternally
external fun createConnection(options: `T$19`, connectionListener: Function<*>? = definedExternally /* null */): dynamic = definedExternally
external fun createConnection(port: Number, host: String? = definedExternally /* null */, connectionListener: Function<*>? = definedExternally /* null */): dynamic = definedExternally
external fun createConnection(path: String, connectionListener: Function<*>? = definedExternally /* null */): dynamic = definedExternally
external fun isIP(input: String): Number = definedExternally
external fun isIPv4(input: String): Boolean = definedExternally
external fun isIPv6(input: String): Boolean = definedExternally
external fun createServer(): Server = definedExternally
