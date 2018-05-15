@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("repl")
package repl

external interface ReplOptions {
    var prompt: String? get() = definedExternally; set(value) = definedExternally
    var input: NodeJS.ReadableStream? get() = definedExternally; set(value) = definedExternally
    var output: NodeJS.WritableStream? get() = definedExternally; set(value) = definedExternally
    var terminal: Boolean? get() = definedExternally; set(value) = definedExternally
    var eval: Function<*>? get() = definedExternally; set(value) = definedExternally
    var useColors: Boolean? get() = definedExternally; set(value) = definedExternally
    var useGlobal: Boolean? get() = definedExternally; set(value) = definedExternally
    var ignoreUndefined: Boolean? get() = definedExternally; set(value) = definedExternally
    var writer: Function<*>? get() = definedExternally; set(value) = definedExternally
    var completer: Function<*>? get() = definedExternally; set(value) = definedExternally
    var replMode: Any? get() = definedExternally; set(value) = definedExternally
    var breakEvalOnSigint: Any? get() = definedExternally; set(value) = definedExternally
}
external interface `T$15` {
    var help: String
    var action: Function<*>
}
external interface REPLServer : readline.ReadLine {
    var context: Any
    fun defineCommand(keyword: String, cmd: Function<*>)
    fun defineCommand(keyword: String, cmd: `T$15`)
    fun displayPrompt(preserveCursor: Boolean? = definedExternally /* null */)
    override fun addListener(event: String, listener: Function<*>): REPLServer /* this */
    override fun addListener(event: String /* "exit" */, listener: () -> Unit): REPLServer /* this */
    override fun addListener(event: String /* "reset" */, listener: Function<*>): REPLServer /* this */
    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any?, vararg args: Any): Boolean
    override fun emit(event: String /* "exit" */): Boolean
    override fun emit(event: String /* "reset" */, context: Any): Boolean
    override fun on(event: String, listener: Function<*>): REPLServer /* this */
    override fun on(event: String /* "exit" */, listener: () -> Unit): REPLServer /* this */
    override fun on(event: String /* "reset" */, listener: Function<*>): REPLServer /* this */
    override fun once(event: String, listener: Function<*>): REPLServer /* this */
    override fun once(event: String /* "exit" */, listener: () -> Unit): REPLServer /* this */
    override fun once(event: String /* "reset" */, listener: Function<*>): REPLServer /* this */
    override fun prependListener(event: String, listener: Function<*>): REPLServer /* this */
    override fun prependListener(event: String /* "exit" */, listener: () -> Unit): REPLServer /* this */
    override fun prependListener(event: String /* "reset" */, listener: Function<*>): REPLServer /* this */
    override fun prependOnceListener(event: String, listener: Function<*>): REPLServer /* this */
    override fun prependOnceListener(event: String /* "exit" */, listener: () -> Unit): REPLServer /* this */
    override fun prependOnceListener(event: String /* "reset" */, listener: Function<*>): REPLServer /* this */
}
external fun start(options: String? = definedExternally /* null */): REPLServer = definedExternally
external fun start(options: ReplOptions? = definedExternally /* null */): REPLServer = definedExternally
external fun start(): REPLServer = definedExternally
