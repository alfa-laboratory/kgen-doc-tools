@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("child_process")
package child_process

import buf.Buffer

external interface ChildProcess : events.internal.EventEmitter {
    var stdin: stream.internal.Writable
    var stdout: stream.internal.Readable
    var stderr: stream.internal.Readable
    var stdio: dynamic /* JsTuple<stream.internal.Writable, stream.internal.Readable, stream.internal.Readable> */
    var killed: Boolean
    var pid: Number
    fun kill(signal: String? = definedExternally /* null */)
    fun send(message: Any, sendHandle: Any? = definedExternally /* null */): Boolean
    var connected: Boolean
    fun disconnect()
    fun unref()
    fun ref()
    override fun addListener(event: String, listener: Function<*>): ChildProcess /* this */
    fun addListener(event: String /* "close" */, listener: (code: Number, signal: String) -> Unit): ChildProcess /* this */
    fun addListener(event: String /* "disconnect" */, listener: () -> Unit): ChildProcess /* this */
    fun addListener(event: String /* "error" */, listener: (err: Error) -> Unit): ChildProcess /* this */
    fun addListener(event: String /* "exit" */, listener: (code: Number, signal: String) -> Unit): ChildProcess /* this */
    fun addListener(event: String /* "message" */, listener: (message: Any, sendHandle: dynamic /* net.Server | `"net".Socket` */) -> Unit): ChildProcess /* this */
    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any?, vararg args: Any): Boolean
    fun emit(event: String /* "close" */, code: Number, signal: String): Boolean
    fun emit(event: String /* "disconnect" */): Boolean
    fun emit(event: String /* "error" */, err: Error): Boolean
    fun emit(event: String /* "exit" */, code: Number, signal: String): Boolean
    fun emit(event: String /* "message" */, message: Any, sendHandle: net.Server): Boolean
    fun emit(event: String /* "message" */, message: Any, sendHandle: dynamic): Boolean
    override fun on(event: String, listener: Function<*>): ChildProcess /* this */
    fun on(event: String /* "close" */, listener: (code: Number, signal: String) -> Unit): ChildProcess /* this */
    fun on(event: String /* "disconnect" */, listener: () -> Unit): ChildProcess /* this */
    fun on(event: String /* "error" */, listener: (err: Error) -> Unit): ChildProcess /* this */
    fun on(event: String /* "exit" */, listener: (code: Number, signal: String) -> Unit): ChildProcess /* this */
    fun on(event: String /* "message" */, listener: (message: Any, sendHandle: dynamic /* net.Server | `"net".Socket` */) -> Unit): ChildProcess /* this */
    override fun once(event: String, listener: Function<*>): ChildProcess /* this */
    fun once(event: String /* "close" */, listener: (code: Number, signal: String) -> Unit): ChildProcess /* this */
    fun once(event: String /* "disconnect" */, listener: () -> Unit): ChildProcess /* this */
    fun once(event: String /* "error" */, listener: (err: Error) -> Unit): ChildProcess /* this */
    fun once(event: String /* "exit" */, listener: (code: Number, signal: String) -> Unit): ChildProcess /* this */
    fun once(event: String /* "message" */, listener: (message: Any, sendHandle: dynamic /* net.Server | `"net".Socket` */) -> Unit): ChildProcess /* this */
    override fun prependListener(event: String, listener: Function<*>): ChildProcess /* this */
    fun prependListener(event: String /* "close" */, listener: (code: Number, signal: String) -> Unit): ChildProcess /* this */
    fun prependListener(event: String /* "disconnect" */, listener: () -> Unit): ChildProcess /* this */
    fun prependListener(event: String /* "error" */, listener: (err: Error) -> Unit): ChildProcess /* this */
    fun prependListener(event: String /* "exit" */, listener: (code: Number, signal: String) -> Unit): ChildProcess /* this */
    fun prependListener(event: String /* "message" */, listener: (message: Any, sendHandle: dynamic /* net.Server | `"net".Socket` */) -> Unit): ChildProcess /* this */
    override fun prependOnceListener(event: String, listener: Function<*>): ChildProcess /* this */
    fun prependOnceListener(event: String /* "close" */, listener: (code: Number, signal: String) -> Unit): ChildProcess /* this */
    fun prependOnceListener(event: String /* "disconnect" */, listener: () -> Unit): ChildProcess /* this */
    fun prependOnceListener(event: String /* "error" */, listener: (err: Error) -> Unit): ChildProcess /* this */
    fun prependOnceListener(event: String /* "exit" */, listener: (code: Number, signal: String) -> Unit): ChildProcess /* this */
    fun prependOnceListener(event: String /* "message" */, listener: (message: Any, sendHandle: dynamic /* net.Server | `"net".Socket` */) -> Unit): ChildProcess /* this */
}
external interface SpawnOptions {
    var cwd: String? get() = definedExternally; set(value) = definedExternally
    var env: Any? get() = definedExternally; set(value) = definedExternally
    var stdio: Any? get() = definedExternally; set(value) = definedExternally
    var detached: Boolean? get() = definedExternally; set(value) = definedExternally
    var uid: Number? get() = definedExternally; set(value) = definedExternally
    var gid: Number? get() = definedExternally; set(value) = definedExternally
    var shell: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
}
external fun spawn(command: String, args: Array<String>? = definedExternally /* null */, options: SpawnOptions? = definedExternally /* null */): ChildProcess = definedExternally
external interface ExecOptions {
    var cwd: String? get() = definedExternally; set(value) = definedExternally
    var env: Any? get() = definedExternally; set(value) = definedExternally
    var shell: String? get() = definedExternally; set(value) = definedExternally
    var timeout: Number? get() = definedExternally; set(value) = definedExternally
    var maxBuffer: Number? get() = definedExternally; set(value) = definedExternally
    var killSignal: String? get() = definedExternally; set(value) = definedExternally
    var uid: Number? get() = definedExternally; set(value) = definedExternally
    var gid: Number? get() = definedExternally; set(value) = definedExternally
}
external interface ExecOptionsWithStringEncoding : ExecOptions {
    var encoding: dynamic /* String /* "ascii" */ | String /* "utf8" */ | String /* "utf16le" */ | String /* "ucs2" */ | String /* "base64" */ | String /* "latin1" */ | String /* "binary" */ | String /* "hex" */ */
}
external interface ExecOptionsWithBufferEncoding : ExecOptions {
    var encoding: String
}
external fun exec(command: String, callback: ((error: Error, stdout: String, stderr: String) -> Unit)? = definedExternally /* null */): ChildProcess = definedExternally
external fun exec(command: String, options: ExecOptionsWithStringEncoding, callback: ((error: Error, stdout: String, stderr: String) -> Unit)? = definedExternally /* null */): ChildProcess = definedExternally
external fun exec(command: String, options: ExecOptionsWithBufferEncoding, callback: ((error: Error, stdout: Buffer, stderr: Buffer) -> Unit)? = definedExternally /* null */): ChildProcess = definedExternally
external fun exec(command: String, options: ExecOptions, callback: ((error: Error, stdout: String, stderr: String) -> Unit)? = definedExternally /* null */): ChildProcess = definedExternally
external interface ExecFileOptions {
    var cwd: String? get() = definedExternally; set(value) = definedExternally
    var env: Any? get() = definedExternally; set(value) = definedExternally
    var timeout: Number? get() = definedExternally; set(value) = definedExternally
    var maxBuffer: Number? get() = definedExternally; set(value) = definedExternally
    var killSignal: String? get() = definedExternally; set(value) = definedExternally
    var uid: Number? get() = definedExternally; set(value) = definedExternally
    var gid: Number? get() = definedExternally; set(value) = definedExternally
}
external interface ExecFileOptionsWithStringEncoding : ExecFileOptions {
    var encoding: dynamic /* String /* "ascii" */ | String /* "utf8" */ | String /* "utf16le" */ | String /* "ucs2" */ | String /* "base64" */ | String /* "latin1" */ | String /* "binary" */ | String /* "hex" */ */
}
external interface ExecFileOptionsWithBufferEncoding : ExecFileOptions {
    var encoding: String
}
external fun execFile(file: String, callback: ((error: Error, stdout: String, stderr: String) -> Unit)? = definedExternally /* null */): ChildProcess = definedExternally
external fun execFile(file: String, options: ExecFileOptionsWithStringEncoding? = definedExternally /* null */, callback: ((error: Error, stdout: String, stderr: String) -> Unit)? = definedExternally /* null */): ChildProcess = definedExternally
external fun execFile(file: String, options: ExecFileOptionsWithBufferEncoding? = definedExternally /* null */, callback: ((error: Error, stdout: Buffer, stderr: Buffer) -> Unit)? = definedExternally /* null */): ChildProcess = definedExternally
external fun execFile(file: String, options: ExecFileOptions? = definedExternally /* null */, callback: ((error: Error, stdout: String, stderr: String) -> Unit)? = definedExternally /* null */): ChildProcess = definedExternally
external fun execFile(file: String, args: Array<String>? = definedExternally /* null */, callback: ((error: Error, stdout: String, stderr: String) -> Unit)? = definedExternally /* null */): ChildProcess = definedExternally
external fun execFile(file: String, args: Array<String>? = definedExternally /* null */, options: ExecFileOptionsWithStringEncoding? = definedExternally /* null */, callback: ((error: Error, stdout: String, stderr: String) -> Unit)? = definedExternally /* null */): ChildProcess = definedExternally
external fun execFile(file: String, args: Array<String>? = definedExternally /* null */, options: ExecFileOptionsWithBufferEncoding? = definedExternally /* null */, callback: ((error: Error, stdout: Buffer, stderr: Buffer) -> Unit)? = definedExternally /* null */): ChildProcess = definedExternally
external fun execFile(file: String, args: Array<String>? = definedExternally /* null */, options: ExecFileOptions? = definedExternally /* null */, callback: ((error: Error, stdout: String, stderr: String) -> Unit)? = definedExternally /* null */): ChildProcess = definedExternally
external interface ForkOptions {
    var cwd: String? get() = definedExternally; set(value) = definedExternally
    var env: Any? get() = definedExternally; set(value) = definedExternally
    var execPath: String? get() = definedExternally; set(value) = definedExternally
    var execArgv: Array<String>? get() = definedExternally; set(value) = definedExternally
    var silent: Boolean? get() = definedExternally; set(value) = definedExternally
    var stdio: Array<Any>? get() = definedExternally; set(value) = definedExternally
    var uid: Number? get() = definedExternally; set(value) = definedExternally
    var gid: Number? get() = definedExternally; set(value) = definedExternally
}
external fun fork(modulePath: String, args: Array<String>? = definedExternally /* null */, options: ForkOptions? = definedExternally /* null */): ChildProcess = definedExternally
external interface SpawnSyncOptions {
    var cwd: String? get() = definedExternally; set(value) = definedExternally
    var input: dynamic /* String | Buffer */ get() = definedExternally; set(value) = definedExternally
    var stdio: Any? get() = definedExternally; set(value) = definedExternally
    var env: Any? get() = definedExternally; set(value) = definedExternally
    var uid: Number? get() = definedExternally; set(value) = definedExternally
    var gid: Number? get() = definedExternally; set(value) = definedExternally
    var timeout: Number? get() = definedExternally; set(value) = definedExternally
    var killSignal: String? get() = definedExternally; set(value) = definedExternally
    var maxBuffer: Number? get() = definedExternally; set(value) = definedExternally
    var encoding: String? get() = definedExternally; set(value) = definedExternally
    var shell: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
}
external interface SpawnSyncOptionsWithStringEncoding : SpawnSyncOptions {
    override var encoding: dynamic /* String /* "ascii" */ | String /* "utf8" */ | String /* "utf16le" */ | String /* "ucs2" */ | String /* "base64" */ | String /* "latin1" */ | String /* "binary" */ | String /* "hex" */ */
}
external interface SpawnSyncOptionsWithBufferEncoding : SpawnSyncOptions {
    override var encoding: dynamic
}
external interface SpawnSyncReturns<T> {
    var pid: Number
    var output: Array<String>
    var stdout: T
    var stderr: T
    var status: Number
    var signal: String
    var error: Error
}
external fun spawnSync(command: String): SpawnSyncReturns<Buffer> = definedExternally
external fun spawnSync(command: String, options: SpawnSyncOptionsWithStringEncoding? = definedExternally /* null */): SpawnSyncReturns<String> = definedExternally
external fun spawnSync(command: String, options: SpawnSyncOptionsWithBufferEncoding? = definedExternally /* null */): SpawnSyncReturns<Buffer> = definedExternally
external fun spawnSync(command: String, options: SpawnSyncOptions? = definedExternally /* null */): SpawnSyncReturns<Buffer> = definedExternally
external fun spawnSync(command: String, args: Array<String>? = definedExternally /* null */, options: SpawnSyncOptionsWithStringEncoding? = definedExternally /* null */): SpawnSyncReturns<String> = definedExternally
external fun spawnSync(command: String, args: Array<String>? = definedExternally /* null */, options: SpawnSyncOptionsWithBufferEncoding? = definedExternally /* null */): SpawnSyncReturns<Buffer> = definedExternally
external fun spawnSync(command: String, args: Array<String>? = definedExternally /* null */, options: SpawnSyncOptions? = definedExternally /* null */): SpawnSyncReturns<Buffer> = definedExternally
external interface ExecSyncOptions {
    var cwd: String? get() = definedExternally; set(value) = definedExternally
    var input: dynamic /* String | Buffer */ get() = definedExternally; set(value) = definedExternally
    var stdio: Any? get() = definedExternally; set(value) = definedExternally
    var env: Any? get() = definedExternally; set(value) = definedExternally
    var shell: String? get() = definedExternally; set(value) = definedExternally
    var uid: Number? get() = definedExternally; set(value) = definedExternally
    var gid: Number? get() = definedExternally; set(value) = definedExternally
    var timeout: Number? get() = definedExternally; set(value) = definedExternally
    var killSignal: String? get() = definedExternally; set(value) = definedExternally
    var maxBuffer: Number? get() = definedExternally; set(value) = definedExternally
    var encoding: String? get() = definedExternally; set(value) = definedExternally
}
external interface ExecSyncOptionsWithStringEncoding : ExecSyncOptions {
    override var encoding: dynamic /* String /* "ascii" */ | String /* "utf8" */ | String /* "utf16le" */ | String /* "ucs2" */ | String /* "base64" */ | String /* "latin1" */ | String /* "binary" */ | String /* "hex" */ */
}
external interface ExecSyncOptionsWithBufferEncoding : ExecSyncOptions {
    override var encoding: dynamic
}
external fun execSync(command: String): Buffer = definedExternally
external fun execSync(command: String, options: ExecSyncOptionsWithStringEncoding? = definedExternally /* null */): String = definedExternally
external fun execSync(command: String, options: ExecSyncOptionsWithBufferEncoding? = definedExternally /* null */): Buffer = definedExternally
external fun execSync(command: String, options: ExecSyncOptions? = definedExternally /* null */): Buffer = definedExternally
external interface ExecFileSyncOptions {
    var cwd: String? get() = definedExternally; set(value) = definedExternally
    var input: dynamic /* String | Buffer */ get() = definedExternally; set(value) = definedExternally
    var stdio: Any? get() = definedExternally; set(value) = definedExternally
    var env: Any? get() = definedExternally; set(value) = definedExternally
    var uid: Number? get() = definedExternally; set(value) = definedExternally
    var gid: Number? get() = definedExternally; set(value) = definedExternally
    var timeout: Number? get() = definedExternally; set(value) = definedExternally
    var killSignal: String? get() = definedExternally; set(value) = definedExternally
    var maxBuffer: Number? get() = definedExternally; set(value) = definedExternally
    var encoding: String? get() = definedExternally; set(value) = definedExternally
}
external interface ExecFileSyncOptionsWithStringEncoding : ExecFileSyncOptions {
    override var encoding: dynamic /* String /* "ascii" */ | String /* "utf8" */ | String /* "utf16le" */ | String /* "ucs2" */ | String /* "base64" */ | String /* "latin1" */ | String /* "binary" */ | String /* "hex" */ */
}
external interface ExecFileSyncOptionsWithBufferEncoding : ExecFileSyncOptions {
    override var encoding: dynamic
}
external fun execFileSync(command: String): Buffer = definedExternally
external fun execFileSync(command: String, options: ExecFileSyncOptionsWithStringEncoding? = definedExternally /* null */): String = definedExternally
external fun execFileSync(command: String, options: ExecFileSyncOptionsWithBufferEncoding? = definedExternally /* null */): Buffer = definedExternally
external fun execFileSync(command: String, options: ExecFileSyncOptions? = definedExternally /* null */): Buffer = definedExternally
external fun execFileSync(command: String, args: Array<String>? = definedExternally /* null */, options: ExecFileSyncOptionsWithStringEncoding? = definedExternally /* null */): String = definedExternally
external fun execFileSync(command: String, args: Array<String>? = definedExternally /* null */, options: ExecFileSyncOptionsWithBufferEncoding? = definedExternally /* null */): Buffer = definedExternally
external fun execFileSync(command: String, args: Array<String>? = definedExternally /* null */, options: ExecFileSyncOptions? = definedExternally /* null */): Buffer = definedExternally
external fun execFile(file: String): ChildProcess = definedExternally
external fun spawnSync(command: String): SpawnSyncReturns<Buffer> = definedExternally
external fun spawnSync(command: String): SpawnSyncReturns<String> = definedExternally
external fun execSync(command: String): Buffer = definedExternally
external fun execFileSync(command: String): Buffer = definedExternally
external fun execFileSync(command: String): String = definedExternally
