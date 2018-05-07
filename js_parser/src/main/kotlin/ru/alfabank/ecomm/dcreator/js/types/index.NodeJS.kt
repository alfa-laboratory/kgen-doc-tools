@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsQualifier("NodeJS")
package NodeJS

import buf.Buffer
import buf.NodeModule
import org.khronos.webgl.ArrayBuffer
import kotlin.js.Console

external interface InspectOptions {
    var showHidden: Boolean? get() = definedExternally; set(value) = definedExternally
    var depth: Number? get() = definedExternally; set(value) = definedExternally
    var colors: Boolean? get() = definedExternally; set(value) = definedExternally
    var customInspect: Boolean? get() = definedExternally; set(value) = definedExternally
    var showProxy: Boolean? get() = definedExternally; set(value) = definedExternally
    var maxArrayLength: Number? get() = definedExternally; set(value) = definedExternally
    var breakLength: Number? get() = definedExternally; set(value) = definedExternally
}
external interface ConsoleConstructor {
    var prototype: Console
}
external interface CallSite {
    fun getThis(): Any
    fun getTypeName(): String?
    fun getFunction(): Function<*>?
    fun getFunctionName(): String?
    fun getMethodName(): String?
    fun getFileName(): String?
    fun getLineNumber(): Number?
    fun getColumnNumber(): Number?
    fun getEvalOrigin(): String?
    fun isToplevel(): Boolean
    fun isEval(): Boolean
    fun isNative(): Boolean
    fun isConstructor(): Boolean
}

external open class EventEmitter {
    open fun addListener(event: String, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun addListener(event: Any?, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun on(event: String, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun on(event: Any?, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun once(event: String, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun once(event: Any?, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun removeListener(event: String, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun removeListener(event: Any?, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun removeAllListeners(event: String? = definedExternally /* null */): EventEmitter /* this */ = definedExternally
    open fun removeAllListeners(event: Any? = definedExternally /* null */): EventEmitter /* this */ = definedExternally
    open fun setMaxListeners(n: Number): EventEmitter /* this */ = definedExternally
    open fun getMaxListeners(): Number = definedExternally
    open fun listeners(event: String): Array<Function<*>> = definedExternally
    open fun listeners(event: Any?): Array<Function<*>> = definedExternally
    open fun emit(event: String, vararg args: Any): Boolean = definedExternally
    open fun emit(event: Any?, vararg args: Any): Boolean = definedExternally
    open fun listenerCount(type: String): Number = definedExternally
    open fun listenerCount(type: Any?): Number = definedExternally
    open fun prependListener(event: String, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun prependListener(event: Any?, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun prependOnceListener(event: String, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun prependOnceListener(event: Any?, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun eventNames(): Array<dynamic /* String | Any? */> = definedExternally
    open fun removeAllListeners(): EventEmitter /* this */ = definedExternally
}
external interface `T$0` {
    var end: Boolean? get() = definedExternally; set(value) = definedExternally
}
external interface ReadableStream : EventEmitter {
    var readable: Boolean
    fun read(size: Number? = definedExternally /* null */): dynamic /* String | Buffer */
    fun setEncoding(encoding: String?): ReadableStream /* this */
    fun pause(): ReadableStream /* this */
    fun resume(): ReadableStream /* this */
    fun isPaused(): Boolean
    fun <T : WritableStream> pipe(destination: T, options: `T$0`? = definedExternally /* null */): T
    fun <T : WritableStream> unpipe(destination: T? = definedExternally /* null */): ReadableStream /* this */
    fun unshift(chunk: String)
    fun unshift(chunk: Buffer)
    fun wrap(oldStream: ReadableStream): ReadableStream
}
external interface WritableStream : EventEmitter {
    var writable: Boolean
    fun write(buffer: String, cb: Function<*>? = definedExternally /* null */): Boolean
    fun write(buffer: Buffer, cb: Function<*>? = definedExternally /* null */): Boolean
    fun write(str: String, encoding: String? = definedExternally /* null */, cb: Function<*>? = definedExternally /* null */): Boolean
    fun end(cb: Function<*>? = definedExternally /* null */)
    fun end(buffer: Buffer, cb: Function<*>? = definedExternally /* null */)
    fun end(str: String, cb: Function<*>? = definedExternally /* null */)
    fun end(str: String, encoding: String? = definedExternally /* null */, cb: Function<*>? = definedExternally /* null */)
    fun end(str: String)
}
external interface ReadWriteStream : ReadableStream, WritableStream
external interface Events : EventEmitter
external interface Domain : Events {
    fun run(fn: Function<*>)
    fun add(emitter: Events)
    fun remove(emitter: Events)
    fun bind(cb: (err: Error, data: Any) -> Any): Any
    fun intercept(cb: (data: Any) -> Any): Any
    fun dispose()
    override fun addListener(event: String, listener: Function<*>): Domain /* this */
    override fun on(event: String, listener: Function<*>): Domain /* this */
    override fun once(event: String, listener: Function<*>): Domain /* this */
    override fun removeListener(event: String, listener: Function<*>): Domain /* this */
    override fun removeAllListeners(event: String?): Domain /* this */
}
external interface MemoryUsage {
    var rss: Number
    var heapTotal: Number
    var heapUsed: Number
}
external interface CpuUsage {
    var user: Number
    var system: Number
}
external interface ProcessVersions {
    var http_parser: String
    var node: String
    var v8: String
    var ares: String
    var uv: String
    var zlib: String
    var modules: String
    var openssl: String
}
external interface Socket : ReadWriteStream {
    var isTTY: Boolean? get() = definedExternally; set(value) = definedExternally
}
external interface WriteStream : Socket {
    var columns: Number? get() = definedExternally; set(value) = definedExternally
    var rows: Number? get() = definedExternally; set(value) = definedExternally
}
external interface ReadStream : Socket {
    var isRaw: Boolean? get() = definedExternally; set(value) = definedExternally
    val setRawMode: ((mode: Boolean) -> Unit)? get() = definedExternally
}
external interface `T$1` {
    var cflags: Array<Any>
    var default_configuration: String
    var defines: Array<String>
    var include_dirs: Array<String>
    var libraries: Array<String>
}
external interface `T$2` {
    var clang: Number
    var host_arch: String
    var node_install_npm: Boolean
    var node_install_waf: Boolean
    var node_prefix: String
    var node_shared_openssl: Boolean
    var node_shared_v8: Boolean
    var node_shared_zlib: Boolean
    var node_use_dtrace: Boolean
    var node_use_etw: Boolean
    var node_use_openssl: Boolean
    var target_arch: String
    var v8_no_strict_aliasing: Number
    var v8_use_snapshot: Boolean
    var visibility: String
}
external interface `T$3` {
    var target_defaults: `T$1`
    var variables: `T$2`
}
external interface Process : EventEmitter {
    var stdout: WriteStream
    var stderr: WriteStream
    var stdin: ReadStream
    fun openStdin(): Socket
    var argv: Array<String>
    var argv0: String
    var execArgv: Array<String>
    var execPath: String
    fun abort()
    fun chdir(directory: String)
    fun cwd(): String
    fun emitWarning(warning: String, name: String? = definedExternally /* null */, ctor: Function<*>? = definedExternally /* null */)
    fun emitWarning(warning: Error, name: String? = definedExternally /* null */, ctor: Function<*>? = definedExternally /* null */)
    var env: Any
    fun exit(code: Number? = definedExternally /* null */): Any?
    var exitCode: Number
    fun getgid(): Number
    fun setgid(id: Number)
    fun setgid(id: String)
    fun getuid(): Number
    fun setuid(id: Number)
    fun setuid(id: String)
    var version: String
    var versions: ProcessVersions
    var config: `T$3`
    fun kill(pid: Number, signal: String? = definedExternally /* null */)
    fun kill(pid: Number, signal: Number? = definedExternally /* null */)
    var pid: Number
    var title: String
    var arch: String
    var platform: dynamic /* String /* "aix" */ | String /* "android" */ | String /* "darwin" */ | String /* "freebsd" */ | String /* "linux" */ | String /* "openbsd" */ | String /* "sunos" */ | String /* "win32" */ */
    var mainModule: NodeModule? get() = definedExternally; set(value) = definedExternally
    fun memoryUsage(): MemoryUsage
    fun cpuUsage(previousValue: CpuUsage? = definedExternally /* null */): CpuUsage
    fun nextTick(callback: Function<*>, vararg args: Any)
    fun umask(mask: Number? = definedExternally /* null */): Number
    fun uptime(): Number
    fun hrtime(time: dynamic /* JsTuple<Number, Number> */ = definedExternally /* null */): dynamic /* JsTuple<Number, Number> */
    var domain: Domain
    val send: ((message: Any, sendHandle: Any? /*= null*/) -> Unit)? get() = definedExternally
    fun disconnect()
    var connected: Boolean
    fun kill(pid: Number)
}
external interface `T$4` {
    var prototype: Buffer
    fun from(arrayBuffer: ArrayBuffer, byteOffset: Number? = definedExternally /* null */, length: Number? = definedExternally /* null */): Buffer
    fun from(data: String): Buffer
    fun from(data: Array<Any>): Buffer
    fun from(data: Buffer): Buffer
    fun from(data: ArrayBuffer): Buffer
    fun from(str: String, encoding: String? = definedExternally /* null */): Buffer
    fun isBuffer(obj: Any): Boolean
    fun isEncoding(encoding: String): Boolean
    fun byteLength(string: String, encoding: String? = definedExternally /* null */): Number
    fun concat(list: Array<Buffer>, totalLength: Number? = definedExternally /* null */): Buffer
    fun compare(buf1: Buffer, buf2: Buffer): Number
    fun alloc(size: Number, fill: String? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Buffer
    fun alloc(size: Number, fill: Number? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Buffer
    fun alloc(size: Number, fill: Buffer? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Buffer
    fun allocUnsafe(size: Number): Buffer
    fun allocUnsafeSlow(size: Number): Buffer
    fun alloc(size: Number): Buffer
}

external interface Timer {
    fun ref()
    fun unref()
}
