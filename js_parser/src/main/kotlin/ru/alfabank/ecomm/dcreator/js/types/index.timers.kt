@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("timers")
package timers

external fun setTimeout(callback: (args: Any) -> Unit, ms: Number, vararg args: Any): NodeJS.Timer = definedExternally
external fun clearTimeout(timeoutId: NodeJS.Timer): Unit = definedExternally
external fun setInterval(callback: (args: Any) -> Unit, ms: Number, vararg args: Any): NodeJS.Timer = definedExternally
external fun clearInterval(intervalId: NodeJS.Timer): Unit = definedExternally
external fun setImmediate(callback: (args: Any) -> Unit, vararg args: Any): Any = definedExternally
external fun clearImmediate(immediateId: Any): Unit = definedExternally
