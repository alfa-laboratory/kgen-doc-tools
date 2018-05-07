@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("util")
package util

external interface InspectOptions : NodeJS.InspectOptions
external fun format(format: Any, vararg param: Any): String = definedExternally
external fun debug(string: String): Unit = definedExternally
external fun error(vararg param: Any): Unit = definedExternally
external fun puts(vararg param: Any): Unit = definedExternally
external fun print(vararg param: Any): Unit = definedExternally
external fun log(string: String): Unit = definedExternally
external fun inspect(`object`: Any, showHidden: Boolean? = definedExternally /* null */, depth: Number? = definedExternally /* null */, color: Boolean? = definedExternally /* null */): String = definedExternally
external fun inspect(`object`: Any, options: InspectOptions): String = definedExternally
external fun isArray(`object`: Any): Boolean = definedExternally
external fun isRegExp(`object`: Any): Boolean = definedExternally
external fun isDate(`object`: Any): Boolean = definedExternally
external fun isError(`object`: Any): Boolean = definedExternally
external fun inherits(constructor: Any, superConstructor: Any): Unit = definedExternally
external fun debuglog(key: String): (msg: String, param: Any) -> Unit = definedExternally
external fun isBoolean(`object`: Any): Boolean = definedExternally
external fun isBuffer(`object`: Any): Boolean = definedExternally
external fun isFunction(`object`: Any): Boolean = definedExternally
external fun isNull(`object`: Any): Boolean = definedExternally
external fun isNullOrUndefined(`object`: Any): Boolean = definedExternally
external fun isNumber(`object`: Any): Boolean = definedExternally
external fun isObject(`object`: Any): Boolean = definedExternally
external fun isPrimitive(`object`: Any): Boolean = definedExternally
external fun isString(`object`: Any): Boolean = definedExternally
external fun isSymbol(`object`: Any): Boolean = definedExternally
external fun isUndefined(`object`: Any): Boolean = definedExternally
external fun <T : Function<*>> deprecate(fn: T, message: String): T = definedExternally
