@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("path")
package path

external interface ParsedPath {
    var root: String
    var dir: String
    var base: String
    var ext: String
    var name: String
}
external interface FormatInputPathObject {
    var root: String? get() = definedExternally; set(value) = definedExternally
    var dir: String? get() = definedExternally; set(value) = definedExternally
    var base: String? get() = definedExternally; set(value) = definedExternally
    var ext: String? get() = definedExternally; set(value) = definedExternally
    var name: String? get() = definedExternally; set(value) = definedExternally
}
external fun normalize(p: String): String = definedExternally
external fun join(vararg paths: String): String = definedExternally
external fun resolve(vararg pathSegments: String): String = definedExternally
external fun isAbsolute(path: String): Boolean = definedExternally
external fun relative(from: String, to: String): String = definedExternally
external fun dirname(p: String): String = definedExternally
external fun basename(p: String, ext: String? = definedExternally /* null */): String = definedExternally
external fun extname(p: String): String = definedExternally
external var sep: dynamic /* String /* "\" */ | String /* "/" */ */ = definedExternally
external var delimiter: dynamic /* String /* ";" */ | String /* ":" */ */ = definedExternally
external fun parse(pathString: String): ParsedPath = definedExternally
external fun format(pathObject: FormatInputPathObject): String = definedExternally
