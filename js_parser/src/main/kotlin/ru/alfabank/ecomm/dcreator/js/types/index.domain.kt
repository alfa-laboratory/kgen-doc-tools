@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("domain")

package domain

import NodeJS.Events

external open class Domain : events.internal.EventEmitter, NodeJS.Domain {
    override fun add(emitter: Events)

    override fun remove(emitter: Events)
    override fun addListener(event: String, listener: Function<*>): NodeJS.Domain
    override fun on(event: String, listener: Function<*>): NodeJS.Domain

    override fun once(event: String, listener: Function<*>): NodeJS.Domain
    override fun removeListener(event: String, listener: Function<*>): NodeJS.Domain

    override fun removeAllListeners(event: String?): NodeJS.Domain

    override fun run(fn: Function<*>): Unit = definedExternally
    open fun add(emitter: events.internal.EventEmitter): Unit = definedExternally
    open fun remove(emitter: events.internal.EventEmitter): Unit = definedExternally
    override fun bind(cb: (err: Error, data: Any) -> Any): Any = definedExternally
    override fun intercept(cb: (data: Any) -> Any): Any = definedExternally
    override fun dispose(): Unit = definedExternally
    open var members: Array<Any> = definedExternally
    open fun enter(): Unit = definedExternally
    open fun exit(): Unit = definedExternally
}

external fun create(): Domain = definedExternally
