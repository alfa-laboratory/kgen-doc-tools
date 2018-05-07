@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("zlib")
package zlib

import buf.Buffer

external interface ZlibOptions {
    var flush: Number? get() = definedExternally; set(value) = definedExternally
    var finishFlush: Number? get() = definedExternally; set(value) = definedExternally
    var chunkSize: Number? get() = definedExternally; set(value) = definedExternally
    var windowBits: Number? get() = definedExternally; set(value) = definedExternally
    var level: Number? get() = definedExternally; set(value) = definedExternally
    var memLevel: Number? get() = definedExternally; set(value) = definedExternally
    var strategy: Number? get() = definedExternally; set(value) = definedExternally
    var dictionary: Any? get() = definedExternally; set(value) = definedExternally
}
external interface Gzip : stream.internal.Transform
external interface Gunzip : stream.internal.Transform
external interface Deflate : stream.internal.Transform
external interface Inflate : stream.internal.Transform
external interface DeflateRaw : stream.internal.Transform
external interface InflateRaw : stream.internal.Transform
external interface Unzip : stream.internal.Transform
external fun createGzip(options: ZlibOptions? = definedExternally /* null */): Gzip = definedExternally
external fun createGunzip(options: ZlibOptions? = definedExternally /* null */): Gunzip = definedExternally
external fun createDeflate(options: ZlibOptions? = definedExternally /* null */): Deflate = definedExternally
external fun createInflate(options: ZlibOptions? = definedExternally /* null */): Inflate = definedExternally
external fun createDeflateRaw(options: ZlibOptions? = definedExternally /* null */): DeflateRaw = definedExternally
external fun createInflateRaw(options: ZlibOptions? = definedExternally /* null */): InflateRaw = definedExternally
external fun createUnzip(options: ZlibOptions? = definedExternally /* null */): Unzip = definedExternally
external fun deflate(buf: String, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun deflate(buf: Buffer, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun deflate(buf: String, options: ZlibOptions, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun deflate(buf: Buffer, options: ZlibOptions, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun deflateSync(buf: String, options: ZlibOptions? = definedExternally /* null */): Buffer = definedExternally
external fun deflateSync(buf: Buffer, options: ZlibOptions? = definedExternally /* null */): Buffer = definedExternally
external fun deflateRaw(buf: String, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun deflateRaw(buf: Buffer, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun deflateRaw(buf: String, options: ZlibOptions, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun deflateRaw(buf: Buffer, options: ZlibOptions, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun deflateRawSync(buf: String, options: ZlibOptions? = definedExternally /* null */): Buffer = definedExternally
external fun deflateRawSync(buf: Buffer, options: ZlibOptions? = definedExternally /* null */): Buffer = definedExternally
external fun gzip(buf: String, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun gzip(buf: Buffer, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun gzip(buf: String, options: ZlibOptions, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun gzip(buf: Buffer, options: ZlibOptions, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun gzipSync(buf: String, options: ZlibOptions? = definedExternally /* null */): Buffer = definedExternally
external fun gzipSync(buf: Buffer, options: ZlibOptions? = definedExternally /* null */): Buffer = definedExternally
external fun gunzip(buf: String, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun gunzip(buf: Buffer, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun gunzip(buf: String, options: ZlibOptions, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun gunzip(buf: Buffer, options: ZlibOptions, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun gunzipSync(buf: String, options: ZlibOptions? = definedExternally /* null */): Buffer = definedExternally
external fun gunzipSync(buf: Buffer, options: ZlibOptions? = definedExternally /* null */): Buffer = definedExternally
external fun inflate(buf: String, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun inflate(buf: Buffer, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun inflate(buf: String, options: ZlibOptions, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun inflate(buf: Buffer, options: ZlibOptions, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun inflateSync(buf: String, options: ZlibOptions? = definedExternally /* null */): Buffer = definedExternally
external fun inflateSync(buf: Buffer, options: ZlibOptions? = definedExternally /* null */): Buffer = definedExternally
external fun inflateRaw(buf: String, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun inflateRaw(buf: Buffer, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun inflateRaw(buf: String, options: ZlibOptions, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun inflateRaw(buf: Buffer, options: ZlibOptions, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun inflateRawSync(buf: String, options: ZlibOptions? = definedExternally /* null */): Buffer = definedExternally
external fun inflateRawSync(buf: Buffer, options: ZlibOptions? = definedExternally /* null */): Buffer = definedExternally
external fun unzip(buf: String, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun unzip(buf: Buffer, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun unzip(buf: String, options: ZlibOptions, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun unzip(buf: Buffer, options: ZlibOptions, callback: (error: Error, result: Buffer) -> Unit): Unit = definedExternally
external fun unzipSync(buf: String, options: ZlibOptions? = definedExternally /* null */): Buffer = definedExternally
external fun unzipSync(buf: Buffer, options: ZlibOptions? = definedExternally /* null */): Buffer = definedExternally
external var Z_NO_FLUSH: Number = definedExternally
external var Z_PARTIAL_FLUSH: Number = definedExternally
external var Z_SYNC_FLUSH: Number = definedExternally
external var Z_FULL_FLUSH: Number = definedExternally
external var Z_FINISH: Number = definedExternally
external var Z_BLOCK: Number = definedExternally
external var Z_TREES: Number = definedExternally
external var Z_OK: Number = definedExternally
external var Z_STREAM_END: Number = definedExternally
external var Z_NEED_DICT: Number = definedExternally
external var Z_ERRNO: Number = definedExternally
external var Z_STREAM_ERROR: Number = definedExternally
external var Z_DATA_ERROR: Number = definedExternally
external var Z_MEM_ERROR: Number = definedExternally
external var Z_BUF_ERROR: Number = definedExternally
external var Z_VERSION_ERROR: Number = definedExternally
external var Z_NO_COMPRESSION: Number = definedExternally
external var Z_BEST_SPEED: Number = definedExternally
external var Z_BEST_COMPRESSION: Number = definedExternally
external var Z_DEFAULT_COMPRESSION: Number = definedExternally
external var Z_FILTERED: Number = definedExternally
external var Z_HUFFMAN_ONLY: Number = definedExternally
external var Z_RLE: Number = definedExternally
external var Z_FIXED: Number = definedExternally
external var Z_DEFAULT_STRATEGY: Number = definedExternally
external var Z_BINARY: Number = definedExternally
external var Z_TEXT: Number = definedExternally
external var Z_ASCII: Number = definedExternally
external var Z_UNKNOWN: Number = definedExternally
external var Z_DEFLATED: Number = definedExternally
