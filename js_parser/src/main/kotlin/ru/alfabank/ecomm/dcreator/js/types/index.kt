@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")

@file:JsModule("")
package buf

import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Uint8Array

external class NodeJS {
    class Global
    class Timer
}

external interface MapConstructor
external interface WeakMapConstructor
external interface SetConstructor
external interface WeakSetConstructor
external interface Iterable<T>
external interface Iterator<T> {
    fun next(value: Any? = definedExternally /* null */): IteratorResult<T>
}
external interface IteratorResult<T>
external interface SymbolConstructor {
    var iterator: Any?
}
external var Symbol: SymbolConstructor = definedExternally
external var global: NodeJS.Global = definedExternally
external var __filename: String = definedExternally
external var __dirname: String = definedExternally
external fun setTimeout(callback: (args: Any) -> Unit, ms: Number, vararg args: Any): NodeJS.Timer = definedExternally
external fun clearTimeout(timeoutId: NodeJS.Timer): Unit = definedExternally
external fun setInterval(callback: (args: Any) -> Unit, ms: Number, vararg args: Any): NodeJS.Timer = definedExternally
external fun clearInterval(intervalId: NodeJS.Timer): Unit = definedExternally
external fun setImmediate(callback: (args: Any) -> Unit, vararg args: Any): Any = definedExternally
external fun clearImmediate(immediateId: Any): Unit = definedExternally
external interface NodeRequireFunction {
    @nativeInvoke
    operator fun invoke(id: String): Any
}
external interface NodeRequire : NodeRequireFunction {
    fun resolve(id: String): String
    var cache: Any
    var extensions: Any
    var main: NodeModule?
}
external var require: NodeRequire = definedExternally
external interface NodeModule {
    var exports: Any
    var require: NodeRequireFunction
    var id: String
    var filename: String
    var loaded: Boolean
    var parent: NodeModule?
    var children: Array<NodeModule>
}
external var module: NodeModule = definedExternally
external var exports: Any = definedExternally
external object SlowBuffer {
    var prototype: Buffer = definedExternally
    fun isBuffer(obj: Any): Boolean = definedExternally
    fun byteLength(string: String, encoding: String? = definedExternally /* null */): Number = definedExternally
    fun concat(list: Array<Buffer>, totalLength: Number? = definedExternally /* null */): Buffer = definedExternally
}
external interface Buffer : NodeBuffer {
    companion object {
        var prototype: Buffer = definedExternally
        fun from(arrayBuffer: ArrayBuffer, byteOffset: Number? = definedExternally /* null */, length: Number? = definedExternally /* null */): Buffer = definedExternally
        fun from(data: String): Buffer = definedExternally
        fun from(data: Array<Any>): Buffer = definedExternally
        fun from(data: Buffer): Buffer = definedExternally
        fun from(data: ArrayBuffer): Buffer = definedExternally
        fun from(str: String, encoding: String? = definedExternally /* null */): Buffer = definedExternally
        fun isBuffer(obj: Any): Boolean = definedExternally
        fun isEncoding(encoding: String): Boolean = definedExternally
        fun byteLength(string: String, encoding: String? = definedExternally /* null */): Number = definedExternally
        fun concat(list: Array<Buffer>, totalLength: Number? = definedExternally /* null */): Buffer = definedExternally
        fun compare(buf1: Buffer, buf2: Buffer): Number = definedExternally
        fun alloc(size: Number, fill: String? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Buffer = definedExternally
        fun alloc(size: Number, fill: Number? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Buffer = definedExternally
        fun alloc(size: Number, fill: Buffer? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Buffer = definedExternally
        fun allocUnsafe(size: Number): Buffer = definedExternally
        fun allocUnsafeSlow(size: Number): Buffer = definedExternally
        fun alloc(size: Number): Buffer = definedExternally
    }
}
external interface IterableIterator<T>
external interface `T$5` {
    var type: String /* "Buffer" */
    var data: Array<Any>
}
external interface NodeBuffer : Uint8Array {
    fun write(string: String, offset: Number? = definedExternally /* null */, length: Number? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Number
    fun toString(encoding: String? = definedExternally /* null */, start: Number? = definedExternally /* null */, end: Number? = definedExternally /* null */): String
    fun toJSON(): `T$5`
    fun equals(otherBuffer: Buffer): Boolean
    fun compare(otherBuffer: Buffer, targetStart: Number? = definedExternally /* null */, targetEnd: Number? = definedExternally /* null */, sourceStart: Number? = definedExternally /* null */, sourceEnd: Number? = definedExternally /* null */): Number
    fun copy(targetBuffer: Buffer, targetStart: Number? = definedExternally /* null */, sourceStart: Number? = definedExternally /* null */, sourceEnd: Number? = definedExternally /* null */): Number
    fun slice(start: Number?, end: Number?): Buffer
    fun writeUIntLE(value: Number, offset: Number, byteLength: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeUIntBE(value: Number, offset: Number, byteLength: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeIntLE(value: Number, offset: Number, byteLength: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeIntBE(value: Number, offset: Number, byteLength: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readUIntLE(offset: Number, byteLength: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readUIntBE(offset: Number, byteLength: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readIntLE(offset: Number, byteLength: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readIntBE(offset: Number, byteLength: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readUInt8(offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readUInt16LE(offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readUInt16BE(offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readUInt32LE(offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readUInt32BE(offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readInt8(offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readInt16LE(offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readInt16BE(offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readInt32LE(offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readInt32BE(offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readFloatLE(offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readFloatBE(offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readDoubleLE(offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun readDoubleBE(offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun swap16(): Buffer
    fun swap32(): Buffer
    fun swap64(): Buffer
    fun writeUInt8(value: Number, offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeUInt16LE(value: Number, offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeUInt16BE(value: Number, offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeUInt32LE(value: Number, offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeUInt32BE(value: Number, offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeInt8(value: Number, offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeInt16LE(value: Number, offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeInt16BE(value: Number, offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeInt32LE(value: Number, offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeInt32BE(value: Number, offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeFloatLE(value: Number, offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeFloatBE(value: Number, offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeDoubleLE(value: Number, offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun writeDoubleBE(value: Number, offset: Number, noAssert: Boolean? = definedExternally /* null */): Number
    fun fill(value: Any, offset: Number? = definedExternally /* null */, end: Number? = definedExternally /* null */): NodeBuffer /* this */
    fun indexOf(value: String, byteOffset: Number? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Number
    fun indexOf(value: Number, byteOffset: Number? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Number
    fun indexOf(value: Buffer, byteOffset: Number? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Number
    fun lastIndexOf(value: String, byteOffset: Number? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Number
    fun lastIndexOf(value: Number, byteOffset: Number? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Number
    fun lastIndexOf(value: Buffer, byteOffset: Number? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Number
    fun entries(): IterableIterator<dynamic /* JsTuple<Number, Number> */>
    fun includes(value: String, byteOffset: Number? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Boolean
    fun includes(value: Number, byteOffset: Number? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Boolean
    fun includes(value: Buffer, byteOffset: Number? = definedExternally /* null */, encoding: String? = definedExternally /* null */): Boolean
    fun keys(): IterableIterator<Number>
    fun values(): IterableIterator<Number>
}
