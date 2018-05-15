@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")

package stream

import NodeJS.ReadableStream
import NodeJS.WritableStream
import NodeJS.`T$0`
import buf.Buffer

external interface `T$37` {
    var end: Boolean? get() = definedExternally; set(value) = definedExternally
}
@JsModule("stream")
external open class internal : events.internal.EventEmitter {
    open fun <T : NodeJS.WritableStream> pipe(destination: T, options: `T$37`? = definedExternally /* null */): T = definedExternally
    open class Stream : internal
    interface ReadableOptions {
        var highWaterMark: Number? get() = definedExternally; set(value) = definedExternally
        var encoding: String? get() = definedExternally; set(value) = definedExternally
        var objectMode: Boolean? get() = definedExternally; set(value) = definedExternally
        var read: ((`this`: Readable, size: Number? /*= null*/) -> Any)? get() = definedExternally; set(value) = definedExternally
    }
    interface `T$38` {
        var end: Boolean? get() = definedExternally; set(value) = definedExternally
    }
    open class Readable(opts: ReadableOptions? = definedExternally /* null */) : Stream, NodeJS.ReadableStream {
        override fun <T : WritableStream> pipe(destination: T, options: `T$0`?): T
        override fun unshift(chunk: String)
        override fun unshift(chunk: Buffer)
        override var readable: Boolean = definedExternally
        open fun _read(size: Number): Unit = definedExternally
        override fun read(size: Number?): Any = definedExternally
        override fun setEncoding(encoding: String?): ReadableStream /* this */ = definedExternally
        override fun pause(): Readable /* this */ = definedExternally
        override fun resume(): Readable /* this */ = definedExternally
        override fun isPaused(): Boolean = definedExternally
        open fun <T : NodeJS.WritableStream> pipe(destination: T, options: `T$38`? = definedExternally /* null */): T = definedExternally
        override fun <T : NodeJS.WritableStream> unpipe(destination: T?): Readable /* this */ = definedExternally
        open fun unshift(chunk: Any): Unit = definedExternally
        override fun wrap(oldStream: NodeJS.ReadableStream): Readable = definedExternally
        open fun push(chunk: Any, encoding: String? = definedExternally /* null */): Boolean = definedExternally
        override fun addListener(event: String, listener: Function<*>): Readable /* this */ = definedExternally
        open fun addListener(event: String /* "close" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun addListener(event: String /* "data" */, listener: (chunk: dynamic /* String | Buffer */) -> Unit): Readable /* this */ = definedExternally
        open fun addListener(event: String /* "end" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun addListener(event: String /* "readable" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun addListener(event: String /* "error" */, listener: (err: Error) -> Unit): Readable /* this */ = definedExternally
        override fun emit(event: String, vararg args: Any): Boolean = definedExternally
        override fun emit(event: Any?, vararg args: Any): Boolean = definedExternally
        open fun emit(event: String /* "close" */): Boolean = definedExternally
        open fun emit(event: String /* "data" */, chunk: String): Boolean = definedExternally
        open fun emit(event: String /* "data" */, chunk: Buffer): Boolean = definedExternally
        open fun emit(event: String /* "end" */): Boolean = definedExternally
        open fun emit(event: String /* "readable" */): Boolean = definedExternally
        open fun emit(event: String /* "error" */, err: Error): Boolean = definedExternally
        override fun on(event: String, listener: Function<*>): Readable /* this */ = definedExternally
        open fun on(event: String /* "close" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun on(event: String /* "data" */, listener: (chunk: dynamic /* String | Buffer */) -> Unit): Readable /* this */ = definedExternally
        open fun on(event: String /* "end" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun on(event: String /* "readable" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun on(event: String /* "error" */, listener: (err: Error) -> Unit): Readable /* this */ = definedExternally
        override fun once(event: String, listener: Function<*>): Readable /* this */ = definedExternally
        open fun once(event: String /* "close" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun once(event: String /* "data" */, listener: (chunk: dynamic /* String | Buffer */) -> Unit): Readable /* this */ = definedExternally
        open fun once(event: String /* "end" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun once(event: String /* "readable" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun once(event: String /* "error" */, listener: (err: Error) -> Unit): Readable /* this */ = definedExternally
        override fun prependListener(event: String, listener: Function<*>): Readable /* this */ = definedExternally
        open fun prependListener(event: String /* "close" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun prependListener(event: String /* "data" */, listener: (chunk: dynamic /* String | Buffer */) -> Unit): Readable /* this */ = definedExternally
        open fun prependListener(event: String /* "end" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun prependListener(event: String /* "readable" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun prependListener(event: String /* "error" */, listener: (err: Error) -> Unit): Readable /* this */ = definedExternally
        override fun prependOnceListener(event: String, listener: Function<*>): Readable /* this */ = definedExternally
        open fun prependOnceListener(event: String /* "close" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun prependOnceListener(event: String /* "data" */, listener: (chunk: dynamic /* String | Buffer */) -> Unit): Readable /* this */ = definedExternally
        open fun prependOnceListener(event: String /* "end" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun prependOnceListener(event: String /* "readable" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun prependOnceListener(event: String /* "error" */, listener: (err: Error) -> Unit): Readable /* this */ = definedExternally
        override fun removeListener(event: String, listener: Function<*>): Readable /* this */ = definedExternally
        open fun removeListener(event: String /* "close" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun removeListener(event: String /* "data" */, listener: (chunk: dynamic /* String | Buffer */) -> Unit): Readable /* this */ = definedExternally
        open fun removeListener(event: String /* "end" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun removeListener(event: String /* "readable" */, listener: () -> Unit): Readable /* this */ = definedExternally
        open fun removeListener(event: String /* "error" */, listener: (err: Error) -> Unit): Readable /* this */ = definedExternally
    }
    interface `T$39` {
        var chunk: dynamic /* String | Buffer */
        var encoding: String
    }
    interface WritableOptions {
        var highWaterMark: Number? get() = definedExternally; set(value) = definedExternally
        var decodeStrings: Boolean? get() = definedExternally; set(value) = definedExternally
        var objectMode: Boolean? get() = definedExternally; set(value) = definedExternally
        var write: ((chunk: dynamic /* String | Buffer */, encoding: String, callback: Function<*>) -> Any)? get() = definedExternally; set(value) = definedExternally
        var writev: ((chunks: Array<`T$39`>, callback: Function<*>) -> Any)? get() = definedExternally; set(value) = definedExternally
    }
    abstract class Writable(opts: WritableOptions? = definedExternally /* null */) : Stream, NodeJS.WritableStream {
        override var writable: Boolean = definedExternally
        open fun _write(chunk: Any, encoding: String, callback: Function<*>): Unit = definedExternally
        open fun write(chunk: Any, cb: Function<*>? = definedExternally /* null */): Boolean = definedExternally
        open fun write(chunk: Any, encoding: String? = definedExternally /* null */, cb: Function<*>? = definedExternally /* null */): Boolean = definedExternally
        open fun setDefaultEncoding(encoding: String): Writable /* this */ = definedExternally
        override fun end(cb: Function<*>?): Unit = definedExternally
        open fun end(chunk: Any, cb: Function<*>? = definedExternally /* null */): Unit = definedExternally
        open fun end(chunk: Any, encoding: String? = definedExternally /* null */, cb: Function<*>? = definedExternally /* null */): Unit = definedExternally
        override fun addListener(event: String, listener: Function<*>): Writable /* this */ = definedExternally
        open fun addListener(event: String /* "close" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun addListener(event: String /* "drain" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun addListener(event: String /* "error" */, listener: (err: Error) -> Unit): Writable /* this */ = definedExternally
        open fun addListener(event: String /* "finish" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun addListener(event: String /* "pipe" */, listener: (src: Readable) -> Unit): Writable /* this */ = definedExternally
        open fun addListener(event: String /* "unpipe" */, listener: (src: Readable) -> Unit): Writable /* this */ = definedExternally
        override fun emit(event: String, vararg args: Any): Boolean = definedExternally
        override fun emit(event: Any?, vararg args: Any): Boolean = definedExternally
        open fun emit(event: String /* "close" */): Boolean = definedExternally
        open fun emit(event: String /* "drain" */, chunk: String): Boolean = definedExternally
        open fun emit(event: String /* "drain" */, chunk: Buffer): Boolean = definedExternally
        open fun emit(event: String /* "error" */, err: Error): Boolean = definedExternally
        open fun emit(event: String /* "finish" */): Boolean = definedExternally
        open fun emit(event: String /* "pipe" */, src: Readable): Boolean = definedExternally
        open fun emit(event: String /* "unpipe" */, src: Readable): Boolean = definedExternally
        override fun on(event: String, listener: Function<*>): Writable /* this */ = definedExternally
        open fun on(event: String /* "close" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun on(event: String /* "drain" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun on(event: String /* "error" */, listener: (err: Error) -> Unit): Writable /* this */ = definedExternally
        open fun on(event: String /* "finish" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun on(event: String /* "pipe" */, listener: (src: Readable) -> Unit): Writable /* this */ = definedExternally
        open fun on(event: String /* "unpipe" */, listener: (src: Readable) -> Unit): Writable /* this */ = definedExternally
        override fun once(event: String, listener: Function<*>): Writable /* this */ = definedExternally
        open fun once(event: String /* "close" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun once(event: String /* "drain" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun once(event: String /* "error" */, listener: (err: Error) -> Unit): Writable /* this */ = definedExternally
        open fun once(event: String /* "finish" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun once(event: String /* "pipe" */, listener: (src: Readable) -> Unit): Writable /* this */ = definedExternally
        open fun once(event: String /* "unpipe" */, listener: (src: Readable) -> Unit): Writable /* this */ = definedExternally
        override fun prependListener(event: String, listener: Function<*>): Writable /* this */ = definedExternally
        open fun prependListener(event: String /* "close" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun prependListener(event: String /* "drain" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun prependListener(event: String /* "error" */, listener: (err: Error) -> Unit): Writable /* this */ = definedExternally
        open fun prependListener(event: String /* "finish" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun prependListener(event: String /* "pipe" */, listener: (src: Readable) -> Unit): Writable /* this */ = definedExternally
        open fun prependListener(event: String /* "unpipe" */, listener: (src: Readable) -> Unit): Writable /* this */ = definedExternally
        override fun prependOnceListener(event: String, listener: Function<*>): Writable /* this */ = definedExternally
        open fun prependOnceListener(event: String /* "close" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun prependOnceListener(event: String /* "drain" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun prependOnceListener(event: String /* "error" */, listener: (err: Error) -> Unit): Writable /* this */ = definedExternally
        open fun prependOnceListener(event: String /* "finish" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun prependOnceListener(event: String /* "pipe" */, listener: (src: Readable) -> Unit): Writable /* this */ = definedExternally
        open fun prependOnceListener(event: String /* "unpipe" */, listener: (src: Readable) -> Unit): Writable /* this */ = definedExternally
        override fun removeListener(event: String, listener: Function<*>): Writable /* this */ = definedExternally
        open fun removeListener(event: String /* "close" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun removeListener(event: String /* "drain" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun removeListener(event: String /* "error" */, listener: (err: Error) -> Unit): Writable /* this */ = definedExternally
        open fun removeListener(event: String /* "finish" */, listener: () -> Unit): Writable /* this */ = definedExternally
        open fun removeListener(event: String /* "pipe" */, listener: (src: Readable) -> Unit): Writable /* this */ = definedExternally
        open fun removeListener(event: String /* "unpipe" */, listener: (src: Readable) -> Unit): Writable /* this */ = definedExternally
        open fun write(chunk: Any): Boolean = definedExternally
        open fun end(chunk: Any): Unit = definedExternally
    }
    interface DuplexOptions : ReadableOptions, WritableOptions {
        override var highWaterMark: Number?
        override var objectMode: Boolean?
        var allowHalfOpen: Boolean? get() = definedExternally; set(value) = definedExternally
        var readableObjectMode: Boolean? get() = definedExternally; set(value) = definedExternally
        var writableObjectMode: Boolean? get() = definedExternally; set(value) = definedExternally
    }
//    open class Duplex(opts: DuplexOptions? = definedExternally /* null */) : Readable, Writable {
//        override var writable: Boolean = definedExternally
//        override fun _write(chunk: Any, encoding: String, callback: Function<*>): Unit = definedExternally
//        override fun write(chunk: Any, cb: Function<*>?): Boolean = definedExternally
//        override fun write(chunk: Any, encoding: String?, cb: Function<*>?): Boolean = definedExternally
//        override fun setDefaultEncoding(encoding: String): Duplex /* this */ = definedExternally
//        override fun end(cb: Function<*>?): Unit = definedExternally
//        override fun end(chunk: Any, cb: Function<*>?): Unit = definedExternally
//        override fun end(chunk: Any, encoding: String?, cb: Function<*>?): Unit = definedExternally
//        override fun write(chunk: Any): Boolean = definedExternally
//        override fun end(chunk: Any): Unit = definedExternally
//    }
    interface TransformOptions : DuplexOptions {
        var transform: ((chunk: dynamic /* String | Buffer */, encoding: String, callback: Function<*>) -> Any)? get() = definedExternally; set(value) = definedExternally
        var flush: ((callback: Function<*>) -> Any)? get() = definedExternally; set(value) = definedExternally
    }
    open class Transform(opts: TransformOptions? = definedExternally /* null */)  {
        open fun _transform(chunk: Any, encoding: String, callback: Function<*>): Unit = definedExternally
    }
    open class PassThrough : Transform
}
