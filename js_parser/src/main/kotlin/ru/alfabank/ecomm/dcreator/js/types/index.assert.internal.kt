@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("assert")
package assert.internal

import kotlin.js.RegExp

external interface `T$40` {
    var message: String? get() = definedExternally; set(value) = definedExternally
    var actual: Any? get() = definedExternally; set(value) = definedExternally
    var expected: Any? get() = definedExternally; set(value) = definedExternally
    var operator: String? get() = definedExternally; set(value) = definedExternally
    var stackStartFunction: Function<*>? get() = definedExternally; set(value) = definedExternally
}
external open class AssertionError(options: `T$40`? = definedExternally) {
    var name: String = definedExternally
    open var actual: Any = definedExternally
    open var expected: Any = definedExternally
    open var operator: String = definedExternally
    open var generatedMessage: Boolean = definedExternally
}
external fun fail(actual: Any, expected: Any, message: String? = definedExternally /* null */, operator: String? = definedExternally /* null */): Any? = definedExternally
external fun ok(value: Any, message: String? = definedExternally /* null */): Unit = definedExternally
external fun equal(actual: Any, expected: Any, message: String? = definedExternally /* null */): Unit = definedExternally
external fun notEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Unit = definedExternally
external fun deepEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Unit = definedExternally
external fun notDeepEqual(acutal: Any, expected: Any, message: String? = definedExternally /* null */): Unit = definedExternally
external fun strictEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Unit = definedExternally
external fun notStrictEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Unit = definedExternally
external fun deepStrictEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Unit = definedExternally
external fun notDeepStrictEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Unit = definedExternally
external fun throws(block: Function<*>, message: String? = definedExternally /* null */): Unit = definedExternally
external fun throws(block: Function<*>, error: Function<*>, message: String? = definedExternally /* null */): Unit = definedExternally
external fun throws(block: Function<*>, error: RegExp, message: String? = definedExternally /* null */): Unit = definedExternally
external fun throws(block: Function<*>, error: (err: Any) -> Boolean, message: String? = definedExternally /* null */): Unit = definedExternally
external fun doesNotThrow(block: Function<*>, message: String? = definedExternally /* null */): Unit = definedExternally
external fun doesNotThrow(block: Function<*>, error: Function<*>, message: String? = definedExternally /* null */): Unit = definedExternally
external fun doesNotThrow(block: Function<*>, error: RegExp, message: String? = definedExternally /* null */): Unit = definedExternally
external fun doesNotThrow(block: Function<*>, error: (err: Any) -> Boolean, message: String? = definedExternally /* null */): Unit = definedExternally
external fun ifError(value: Any): Unit = definedExternally
