@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("buffer")
package buffer

import buf.Buffer
import org.khronos.webgl.ArrayBuffer

external var INSPECT_MAX_BYTES: Number = definedExternally
external interface `T$6` {
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
external var BuffType: `T$6` = definedExternally
external interface `T$7` {
    var prototype: Buffer
    fun isBuffer(obj: Any): Boolean
    fun byteLength(string: String, encoding: String? = definedExternally /* null */): Number
    fun concat(list: Array<Buffer>, totalLength: Number? = definedExternally /* null */): Buffer
}
external var SlowBuffType: `T$7` = definedExternally
