@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("_debugger")
package _debugger

external interface Packet {
    var raw: String
    var headers: Array<String>
    var body: Message
}
external interface Message {
    var seq: Number
    var type: String
}
external interface RequestInfo {
    var command: String
    var arguments: Any
}
external interface Request : Message, RequestInfo
external interface Event : Message {
    var event: String
    var body: Any? get() = definedExternally; set(value) = definedExternally
}
external interface Response : Message {
    var request_seq: Number
    var success: Boolean
    var message: String? get() = definedExternally; set(value) = definedExternally
    var body: Any? get() = definedExternally; set(value) = definedExternally
}
external interface BreakpointMessageBody {
    var type: String
    var target: Number
    var line: Number
}
external open class Protocol {
    open var res: Packet = definedExternally
    open var state: String = definedExternally
    open fun execute(data: String): Unit = definedExternally
    open fun serialize(rq: Request): String = definedExternally
    open var onResponse: (pkt: Packet) -> Unit = definedExternally
}
external var NO_FRAME: Number = definedExternally
external var port: Number = definedExternally
external interface ScriptDesc {
    var name: String
    var id: Number
    var isNative: Boolean? get() = definedExternally; set(value) = definedExternally
    var handle: Number? get() = definedExternally; set(value) = definedExternally
    var type: String
    var lineOffset: Number? get() = definedExternally; set(value) = definedExternally
    var columnOffset: Number? get() = definedExternally; set(value) = definedExternally
    var lineCount: Number? get() = definedExternally; set(value) = definedExternally
}
external interface Breakpoint {
    var id: Number
    var scriptId: Number
    var script: ScriptDesc
    var line: Number
    var condition: String? get() = definedExternally; set(value) = definedExternally
    var scriptReq: String? get() = definedExternally; set(value) = definedExternally
}
external interface RequestHandler {
    @nativeInvoke
    operator fun invoke(err: Boolean, body: Message, res: Packet)
    var request_seq: Number? get() = definedExternally; set(value) = definedExternally
}
external interface ResponseBodyHandler {
    @nativeInvoke
    operator fun invoke(err: Boolean, body: Any? = definedExternally /* null */)
    var request_seq: Number? get() = definedExternally; set(value) = definedExternally
}
external interface ExceptionInfo {
    var text: String
}
external interface BreakResponse {
    var script: ScriptDesc? get() = definedExternally; set(value) = definedExternally
    var exception: ExceptionInfo? get() = definedExternally; set(value) = definedExternally
    var sourceLine: Number
    var sourceLineText: String
    var sourceColumn: Number
}
external fun SourceInfo(body: BreakResponse): String = definedExternally
external interface ClientInstance : NodeJS.EventEmitter {
    var protocol: Protocol
    var scripts: Array<ScriptDesc>
    var handles: Array<ScriptDesc>
    var breakpoints: Array<Breakpoint>
    var currentSourceLine: Number
    var currentSourceColumn: Number
    var currentSourceLineText: String
    var currentFrame: Number
    var currentScript: String
    fun connect(port: Number, host: String)
    fun req(req: Any, cb: RequestHandler)
    fun reqFrameEval(code: String, frame: Number, cb: RequestHandler)
    fun mirrorObject(obj: Any, depth: Number, cb: ResponseBodyHandler)
    fun setBreakpoint(rq: BreakpointMessageBody, cb: RequestHandler)
    fun clearBreakpoint(rq: Request, cb: RequestHandler)
    fun listbreakpoints(cb: RequestHandler)
    fun reqSource(from: Number, to: Number, cb: RequestHandler)
    fun reqScripts(cb: Any)
    fun reqContinue(cb: RequestHandler)
}
external object Client {
}
