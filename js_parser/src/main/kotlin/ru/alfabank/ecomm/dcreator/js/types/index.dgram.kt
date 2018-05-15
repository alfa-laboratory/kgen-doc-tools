@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("dgram")
package dgram

import buf.Buffer

external interface RemoteInfo {
    var address: String
    var family: String
    var port: Number
}
external interface AddressInfo {
    var address: String
    var family: String
    var port: Number
}
external interface BindOptions {
    var port: Number
    var address: String? get() = definedExternally; set(value) = definedExternally
    var exclusive: Boolean? get() = definedExternally; set(value) = definedExternally
}
external interface SocketOptions {
    var type: dynamic /* String /* "udp4" */ | String /* "udp6" */ */
    var reuseAddr: Boolean? get() = definedExternally; set(value) = definedExternally
}
external fun createSocket(type: String /* "udp4" */, callback: ((msg: Buffer, rinfo: RemoteInfo) -> Unit)? = definedExternally /* null */): Socket = definedExternally
external fun createSocket(type: String /* "udp6" */, callback: ((msg: Buffer, rinfo: RemoteInfo) -> Unit)? = definedExternally /* null */): Socket = definedExternally
external fun createSocket(options: SocketOptions, callback: ((msg: Buffer, rinfo: RemoteInfo) -> Unit)? = definedExternally /* null */): Socket = definedExternally
external interface Socket : events.internal.EventEmitter {
    fun send(msg: String, port: Number, address: String, callback: ((error: Error, bytes: Number) -> Unit)? = definedExternally /* null */)
    fun send(msg: Array<Any>, port: Number, address: String, callback: ((error: Error, bytes: Number) -> Unit)? = definedExternally /* null */)
    fun send(msg: Buffer, port: Number, address: String, callback: ((error: Error, bytes: Number) -> Unit)? = definedExternally /* null */)
    fun send(msg: String, offset: Number, length: Number, port: Number, address: String, callback: ((error: Error, bytes: Number) -> Unit)? = definedExternally /* null */)
    fun send(msg: Array<Any>, offset: Number, length: Number, port: Number, address: String, callback: ((error: Error, bytes: Number) -> Unit)? = definedExternally /* null */)
    fun send(msg: Buffer, offset: Number, length: Number, port: Number, address: String, callback: ((error: Error, bytes: Number) -> Unit)? = definedExternally /* null */)
    fun bind(port: Number? = definedExternally /* null */, address: String? = definedExternally /* null */, callback: (() -> Unit)? = definedExternally /* null */)
    fun bind(options: BindOptions, callback: Function<*>? = definedExternally /* null */)
    fun close(callback: (() -> Unit)? = definedExternally /* null */)
    fun address(): AddressInfo
    fun setBroadcast(flag: Boolean)
    fun setTTL(ttl: Number)
    fun setMulticastTTL(ttl: Number)
    fun setMulticastLoopback(flag: Boolean)
    fun addMembership(multicastAddress: String, multicastInterface: String? = definedExternally /* null */)
    fun dropMembership(multicastAddress: String, multicastInterface: String? = definedExternally /* null */)
    fun ref(): Socket /* this */
    fun unref(): Socket /* this */
    override fun addListener(event: String, listener: Function<*>): Socket /* this */
    fun addListener(event: String /* "close" */, listener: () -> Unit): Socket /* this */
    fun addListener(event: String /* "error" */, listener: (err: Error) -> Unit): Socket /* this */
    fun addListener(event: String /* "listening" */, listener: () -> Unit): Socket /* this */
    fun addListener(event: String /* "message" */, listener: (msg: Buffer, rinfo: AddressInfo) -> Unit): Socket /* this */
    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any?, vararg args: Any): Boolean
    fun emit(event: String /* "close" */): Boolean
    fun emit(event: String /* "error" */, err: Error): Boolean
    fun emit(event: String /* "listening" */): Boolean
    fun emit(event: String /* "message" */, msg: Buffer, rinfo: AddressInfo): Boolean
    override fun on(event: String, listener: Function<*>): Socket /* this */
    fun on(event: String /* "close" */, listener: () -> Unit): Socket /* this */
    fun on(event: String /* "error" */, listener: (err: Error) -> Unit): Socket /* this */
    fun on(event: String /* "listening" */, listener: () -> Unit): Socket /* this */
    fun on(event: String /* "message" */, listener: (msg: Buffer, rinfo: AddressInfo) -> Unit): Socket /* this */
    override fun once(event: String, listener: Function<*>): Socket /* this */
    fun once(event: String /* "close" */, listener: () -> Unit): Socket /* this */
    fun once(event: String /* "error" */, listener: (err: Error) -> Unit): Socket /* this */
    fun once(event: String /* "listening" */, listener: () -> Unit): Socket /* this */
    fun once(event: String /* "message" */, listener: (msg: Buffer, rinfo: AddressInfo) -> Unit): Socket /* this */
    override fun prependListener(event: String, listener: Function<*>): Socket /* this */
    fun prependListener(event: String /* "close" */, listener: () -> Unit): Socket /* this */
    fun prependListener(event: String /* "error" */, listener: (err: Error) -> Unit): Socket /* this */
    fun prependListener(event: String /* "listening" */, listener: () -> Unit): Socket /* this */
    fun prependListener(event: String /* "message" */, listener: (msg: Buffer, rinfo: AddressInfo) -> Unit): Socket /* this */
    override fun prependOnceListener(event: String, listener: Function<*>): Socket /* this */
    fun prependOnceListener(event: String /* "close" */, listener: () -> Unit): Socket /* this */
    fun prependOnceListener(event: String /* "error" */, listener: (err: Error) -> Unit): Socket /* this */
    fun prependOnceListener(event: String /* "listening" */, listener: () -> Unit): Socket /* this */
    fun prependOnceListener(event: String /* "message" */, listener: (msg: Buffer, rinfo: AddressInfo) -> Unit): Socket /* this */
}
