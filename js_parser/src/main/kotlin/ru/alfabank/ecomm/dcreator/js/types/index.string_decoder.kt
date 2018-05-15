@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("string_decoder")
package string_decoder

import buf.Buffer

external interface NodeStringDecoder {
    fun write(buffer: Buffer): String
    fun end(buffer: Buffer? = definedExternally /* null */): String
}
external object StringDecoder {
}
