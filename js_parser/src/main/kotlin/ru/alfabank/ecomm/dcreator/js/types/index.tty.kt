//@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
//@file:JsModule("tty")
//package tty
//
//import kotlin.js.*
//import kotlin.js.Json
//import org.khronos.webgl.*
//import org.w3c.dom.*
//import org.w3c.dom.events.*
//import org.w3c.dom.parsing.*
//import org.w3c.dom.svg.*
//import org.w3c.dom.url.*
//import org.w3c.fetch.*
//import org.w3c.files.*
//import org.w3c.notifications.*
//import org.w3c.performance.*
//import org.w3c.workers.*
//import org.w3c.xhr.*
//
//external fun isatty(fd: Number): Boolean = definedExternally
//external open class ReadStream : `"net".Socket` {
//    open var isRaw: Boolean = definedExternally
//    open fun setRawMode(mode: Boolean): Unit = definedExternally
//    open var isTTY: Boolean = definedExternally
//}
//external open class WriteStream : `"net".Socket` {
//    open var columns: Number = definedExternally
//    open var rows: Number = definedExternally
//    open var isTTY: Boolean = definedExternally
//}
