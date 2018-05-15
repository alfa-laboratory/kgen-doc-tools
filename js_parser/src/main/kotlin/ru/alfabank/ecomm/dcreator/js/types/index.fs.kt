@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("fs")
package fs

import buf.Buffer
import kotlin.js.*

external class NodeJS {
    class ErrnoException
}

external interface Stats {
    fun isFile(): Boolean
    fun isDirectory(): Boolean
    fun isBlockDevice(): Boolean
    fun isCharacterDevice(): Boolean
    fun isSymbolicLink(): Boolean
    fun isFIFO(): Boolean
    fun isSocket(): Boolean
    var dev: Number
    var ino: Number
    var mode: Number
    var nlink: Number
    var uid: Number
    var gid: Number
    var rdev: Number
    var size: Number
    var blksize: Number
    var blocks: Number
    var atime: Date
    var mtime: Date
    var ctime: Date
    var birthtime: Date
}
external interface FSWatcher : events.internal.EventEmitter {
    fun close()
    override fun addListener(event: String, listener: Function<*>): FSWatcher /* this */
    fun addListener(event: String /* "change" */, listener: (eventType: String, filename: dynamic /* String | Buffer */) -> Unit): FSWatcher /* this */
    fun addListener(event: String /* "error" */, listener: (error: Error) -> Unit): FSWatcher /* this */
    override fun on(event: String, listener: Function<*>): FSWatcher /* this */
    fun on(event: String /* "change" */, listener: (eventType: String, filename: dynamic /* String | Buffer */) -> Unit): FSWatcher /* this */
    fun on(event: String /* "error" */, listener: (error: Error) -> Unit): FSWatcher /* this */
    override fun once(event: String, listener: Function<*>): FSWatcher /* this */
    fun once(event: String /* "change" */, listener: (eventType: String, filename: dynamic /* String | Buffer */) -> Unit): FSWatcher /* this */
    fun once(event: String /* "error" */, listener: (error: Error) -> Unit): FSWatcher /* this */
    override fun prependListener(event: String, listener: Function<*>): FSWatcher /* this */
    fun prependListener(event: String /* "change" */, listener: (eventType: String, filename: dynamic /* String | Buffer */) -> Unit): FSWatcher /* this */
    fun prependListener(event: String /* "error" */, listener: (error: Error) -> Unit): FSWatcher /* this */
    override fun prependOnceListener(event: String, listener: Function<*>): FSWatcher /* this */
    fun prependOnceListener(event: String /* "change" */, listener: (eventType: String, filename: dynamic /* String | Buffer */) -> Unit): FSWatcher /* this */
    fun prependOnceListener(event: String /* "error" */, listener: (error: Error) -> Unit): FSWatcher /* this */
}
external interface ReadStream : stream.internal.Readable {
    fun close()
    fun destroy()
    var bytesRead: Number
    var path: dynamic /* String | Buffer */
    override fun addListener(event: String, listener: Function<*>): ReadStream /* this */
    override fun addListener(event: String /* "open" */, listener: (fd: Number) -> Unit): ReadStream /* this */
    override fun addListener(event: String /* "close" */, listener: () -> Unit): ReadStream /* this */
    override fun on(event: String, listener: Function<*>): ReadStream /* this */
    override fun on(event: String /* "open" */, listener: (fd: Number) -> Unit): ReadStream /* this */
    override fun on(event: String /* "close" */, listener: () -> Unit): ReadStream /* this */
    override fun once(event: String, listener: Function<*>): ReadStream /* this */
    override fun once(event: String /* "open" */, listener: (fd: Number) -> Unit): ReadStream /* this */
    override fun once(event: String /* "close" */, listener: () -> Unit): ReadStream /* this */
    override fun prependListener(event: String, listener: Function<*>): ReadStream /* this */
    override fun prependListener(event: String /* "open" */, listener: (fd: Number) -> Unit): ReadStream /* this */
    override fun prependListener(event: String /* "close" */, listener: () -> Unit): ReadStream /* this */
    override fun prependOnceListener(event: String, listener: Function<*>): ReadStream /* this */
    override fun prependOnceListener(event: String /* "open" */, listener: (fd: Number) -> Unit): ReadStream /* this */
    override fun prependOnceListener(event: String /* "close" */, listener: () -> Unit): ReadStream /* this */
}
external interface WriteStream : stream.internal.Writable {
    fun close()
    var bytesWritten: Number
    var path: dynamic /* String | Buffer */
    override fun addListener(event: String, listener: Function<*>): WriteStream /* this */
    fun addListener(event: String /* "open" */, listener: (fd: Number) -> Unit): WriteStream /* this */
    override fun addListener(event: String /* "close" */, listener: () -> Unit): WriteStream /* this */
    override fun on(event: String, listener: Function<*>): WriteStream /* this */
    fun on(event: String /* "open" */, listener: (fd: Number) -> Unit): WriteStream /* this */
    override fun on(event: String /* "close" */, listener: () -> Unit): WriteStream /* this */
    override fun once(event: String, listener: Function<*>): WriteStream /* this */
    fun once(event: String /* "open" */, listener: (fd: Number) -> Unit): WriteStream /* this */
    override fun once(event: String /* "close" */, listener: () -> Unit): WriteStream /* this */
    override fun prependListener(event: String, listener: Function<*>): WriteStream /* this */
    fun prependListener(event: String /* "open" */, listener: (fd: Number) -> Unit): WriteStream /* this */
    override fun prependListener(event: String /* "close" */, listener: () -> Unit): WriteStream /* this */
    override fun prependOnceListener(event: String, listener: Function<*>): WriteStream /* this */
    fun prependOnceListener(event: String /* "open" */, listener: (fd: Number) -> Unit): WriteStream /* this */
    override fun prependOnceListener(event: String /* "close" */, listener: () -> Unit): WriteStream /* this */
}
external fun rename(oldPath: String, newPath: String, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun renameSync(oldPath: String, newPath: String): Unit = definedExternally
external fun truncate(path: String, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun truncate(path: Buffer, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun truncate(path: String, len: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun truncate(path: Buffer, len: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun truncateSync(path: String, len: Number? = definedExternally /* null */): Unit = definedExternally
external fun truncateSync(path: Buffer, len: Number? = definedExternally /* null */): Unit = definedExternally
external fun ftruncate(fd: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun ftruncate(fd: Number, len: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun ftruncateSync(fd: Number, len: Number? = definedExternally /* null */): Unit = definedExternally
external fun chown(path: String, uid: Number, gid: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun chown(path: Buffer, uid: Number, gid: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun chownSync(path: String, uid: Number, gid: Number): Unit = definedExternally
external fun chownSync(path: Buffer, uid: Number, gid: Number): Unit = definedExternally
external fun fchown(fd: Number, uid: Number, gid: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun fchownSync(fd: Number, uid: Number, gid: Number): Unit = definedExternally
external fun lchown(path: String, uid: Number, gid: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun lchown(path: Buffer, uid: Number, gid: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun lchownSync(path: String, uid: Number, gid: Number): Unit = definedExternally
external fun lchownSync(path: Buffer, uid: Number, gid: Number): Unit = definedExternally
external fun chmod(path: String, mode: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun chmod(path: Buffer, mode: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun chmod(path: String, mode: String, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun chmod(path: Buffer, mode: String, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun chmodSync(path: String, mode: Number): Unit = definedExternally
external fun chmodSync(path: Buffer, mode: Number): Unit = definedExternally
external fun chmodSync(path: String, mode: String): Unit = definedExternally
external fun chmodSync(path: Buffer, mode: String): Unit = definedExternally
external fun fchmod(fd: Number, mode: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun fchmod(fd: Number, mode: String, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun fchmodSync(fd: Number, mode: Number): Unit = definedExternally
external fun fchmodSync(fd: Number, mode: String): Unit = definedExternally
external fun lchmod(path: String, mode: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun lchmod(path: Buffer, mode: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun lchmod(path: String, mode: String, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun lchmod(path: Buffer, mode: String, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun lchmodSync(path: String, mode: Number): Unit = definedExternally
external fun lchmodSync(path: Buffer, mode: Number): Unit = definedExternally
external fun lchmodSync(path: String, mode: String): Unit = definedExternally
external fun lchmodSync(path: Buffer, mode: String): Unit = definedExternally
external fun stat(path: String, callback: ((err: NodeJS.ErrnoException, stats: Stats) -> Any)? = definedExternally /* null */): Unit = definedExternally
external fun stat(path: Buffer, callback: ((err: NodeJS.ErrnoException, stats: Stats) -> Any)? = definedExternally /* null */): Unit = definedExternally
external fun lstat(path: String, callback: ((err: NodeJS.ErrnoException, stats: Stats) -> Any)? = definedExternally /* null */): Unit = definedExternally
external fun lstat(path: Buffer, callback: ((err: NodeJS.ErrnoException, stats: Stats) -> Any)? = definedExternally /* null */): Unit = definedExternally
external fun fstat(fd: Number, callback: ((err: NodeJS.ErrnoException, stats: Stats) -> Any)? = definedExternally /* null */): Unit = definedExternally
external fun statSync(path: String): Stats = definedExternally
external fun statSync(path: Buffer): Stats = definedExternally
external fun lstatSync(path: String): Stats = definedExternally
external fun lstatSync(path: Buffer): Stats = definedExternally
external fun fstatSync(fd: Number): Stats = definedExternally
external fun link(srcpath: String, dstpath: String, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun link(srcpath: String, dstpath: Buffer, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun link(srcpath: Buffer, dstpath: String, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun link(srcpath: Buffer, dstpath: Buffer, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun linkSync(srcpath: String, dstpath: String): Unit = definedExternally
external fun linkSync(srcpath: String, dstpath: Buffer): Unit = definedExternally
external fun linkSync(srcpath: Buffer, dstpath: String): Unit = definedExternally
external fun linkSync(srcpath: Buffer, dstpath: Buffer): Unit = definedExternally
external fun symlink(srcpath: String, dstpath: String, type: String? = definedExternally /* null */, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun symlink(srcpath: String, dstpath: Buffer, type: String? = definedExternally /* null */, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun symlink(srcpath: Buffer, dstpath: String, type: String? = definedExternally /* null */, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun symlink(srcpath: Buffer, dstpath: Buffer, type: String? = definedExternally /* null */, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun symlinkSync(srcpath: String, dstpath: String, type: String? = definedExternally /* null */): Unit = definedExternally
external fun symlinkSync(srcpath: String, dstpath: Buffer, type: String? = definedExternally /* null */): Unit = definedExternally
external fun symlinkSync(srcpath: Buffer, dstpath: String, type: String? = definedExternally /* null */): Unit = definedExternally
external fun symlinkSync(srcpath: Buffer, dstpath: Buffer, type: String? = definedExternally /* null */): Unit = definedExternally
external fun readlink(path: String, callback: ((err: NodeJS.ErrnoException, linkString: String) -> Any)? = definedExternally /* null */): Unit = definedExternally
external fun readlink(path: Buffer, callback: ((err: NodeJS.ErrnoException, linkString: String) -> Any)? = definedExternally /* null */): Unit = definedExternally
external fun readlinkSync(path: String): String = definedExternally
external fun readlinkSync(path: Buffer): String = definedExternally
external fun realpath(path: String, callback: ((err: NodeJS.ErrnoException, resolvedPath: String) -> Any)? = definedExternally /* null */): Unit = definedExternally
external fun realpath(path: Buffer, callback: ((err: NodeJS.ErrnoException, resolvedPath: String) -> Any)? = definedExternally /* null */): Unit = definedExternally
external interface `T$20` {
    @nativeGetter
    operator fun get(path: String): String?
    @nativeSetter
    operator fun set(path: String, value: String)
}
external fun realpath(path: String, cache: `T$20`, callback: (err: NodeJS.ErrnoException, resolvedPath: String) -> Any): Unit = definedExternally
external fun realpath(path: Buffer, cache: `T$20`, callback: (err: NodeJS.ErrnoException, resolvedPath: String) -> Any): Unit = definedExternally
external fun realpathSync(path: String, cache: `T$20`? = definedExternally /* null */): String = definedExternally
external fun realpathSync(path: Buffer, cache: `T$20`? = definedExternally /* null */): String = definedExternally
external fun unlink(path: String, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun unlink(path: Buffer, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun unlinkSync(path: String): Unit = definedExternally
external fun unlinkSync(path: Buffer): Unit = definedExternally
external fun rmdir(path: String, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun rmdir(path: Buffer, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun rmdirSync(path: String): Unit = definedExternally
external fun rmdirSync(path: Buffer): Unit = definedExternally
external fun mkdir(path: String, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun mkdir(path: Buffer, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun mkdir(path: String, mode: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun mkdir(path: Buffer, mode: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun mkdir(path: String, mode: String, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun mkdir(path: Buffer, mode: String, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun mkdirSync(path: String, mode: Number? = definedExternally /* null */): Unit = definedExternally
external fun mkdirSync(path: Buffer, mode: Number? = definedExternally /* null */): Unit = definedExternally
external fun mkdirSync(path: String, mode: String? = definedExternally /* null */): Unit = definedExternally
external fun mkdirSync(path: Buffer, mode: String? = definedExternally /* null */): Unit = definedExternally
external fun mkdtemp(prefix: String, callback: ((err: NodeJS.ErrnoException, folder: String) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun mkdtempSync(prefix: String): String = definedExternally
external fun readdir(path: String, callback: (err: NodeJS.ErrnoException, files: Array<String>) -> Unit): Unit = definedExternally
external fun readdir(path: Buffer, callback: (err: NodeJS.ErrnoException, files: Array<String>) -> Unit): Unit = definedExternally
external fun readdir(path: String, options: String, callback: (err: NodeJS.ErrnoException, files: Array<String>) -> Unit): Unit = definedExternally
external fun readdir(path: String, options: Any?, callback: (err: NodeJS.ErrnoException, files: Array<String>) -> Unit): Unit = definedExternally
external fun readdir(path: Buffer, options: String, callback: (err: NodeJS.ErrnoException, files: Array<String>) -> Unit): Unit = definedExternally
external fun readdir(path: Buffer, options: Any?, callback: (err: NodeJS.ErrnoException, files: Array<String>) -> Unit): Unit = definedExternally
external fun readdirSync(path: String, options: String? = definedExternally /* null */): Array<String> = definedExternally
external fun readdirSync(path: String, options: Any? = definedExternally /* null */): Array<String> = definedExternally
external fun readdirSync(path: Buffer, options: String? = definedExternally /* null */): Array<String> = definedExternally
external fun readdirSync(path: Buffer, options: Any? = definedExternally /* null */): Array<String> = definedExternally
external fun close(fd: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun closeSync(fd: Number): Unit = definedExternally
external fun open(path: String, flags: String, callback: (err: NodeJS.ErrnoException, fd: Number) -> Unit): Unit = definedExternally
external fun open(path: String, flags: Number, callback: (err: NodeJS.ErrnoException, fd: Number) -> Unit): Unit = definedExternally
external fun open(path: Buffer, flags: String, callback: (err: NodeJS.ErrnoException, fd: Number) -> Unit): Unit = definedExternally
external fun open(path: Buffer, flags: Number, callback: (err: NodeJS.ErrnoException, fd: Number) -> Unit): Unit = definedExternally
external fun open(path: String, flags: String, mode: Number, callback: (err: NodeJS.ErrnoException, fd: Number) -> Unit): Unit = definedExternally
external fun open(path: String, flags: Number, mode: Number, callback: (err: NodeJS.ErrnoException, fd: Number) -> Unit): Unit = definedExternally
external fun open(path: Buffer, flags: String, mode: Number, callback: (err: NodeJS.ErrnoException, fd: Number) -> Unit): Unit = definedExternally
external fun open(path: Buffer, flags: Number, mode: Number, callback: (err: NodeJS.ErrnoException, fd: Number) -> Unit): Unit = definedExternally
external fun openSync(path: String, flags: String, mode: Number? = definedExternally /* null */): Number = definedExternally
external fun openSync(path: String, flags: Number, mode: Number? = definedExternally /* null */): Number = definedExternally
external fun openSync(path: Buffer, flags: String, mode: Number? = definedExternally /* null */): Number = definedExternally
external fun openSync(path: Buffer, flags: Number, mode: Number? = definedExternally /* null */): Number = definedExternally
external fun utimes(path: String, atime: Number, mtime: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun utimes(path: Buffer, atime: Number, mtime: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun utimes(path: String, atime: Date, mtime: Date, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun utimes(path: Buffer, atime: Date, mtime: Date, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun utimesSync(path: String, atime: Number, mtime: Number): Unit = definedExternally
external fun utimesSync(path: Buffer, atime: Number, mtime: Number): Unit = definedExternally
external fun utimesSync(path: String, atime: Date, mtime: Date): Unit = definedExternally
external fun utimesSync(path: Buffer, atime: Date, mtime: Date): Unit = definedExternally
external fun futimes(fd: Number, atime: Number, mtime: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun futimes(fd: Number, atime: Date, mtime: Date, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun futimesSync(fd: Number, atime: Number, mtime: Number): Unit = definedExternally
external fun futimesSync(fd: Number, atime: Date, mtime: Date): Unit = definedExternally
external fun fsync(fd: Number, callback: ((err: NodeJS.ErrnoException? /*= null*/) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun fsyncSync(fd: Number): Unit = definedExternally
external fun write(fd: Number, buffer: Buffer, offset: Number, length: Number, position: Number?, callback: ((err: NodeJS.ErrnoException, written: Number, buffer: Buffer) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun write(fd: Number, buffer: Buffer, offset: Number, length: Number, callback: ((err: NodeJS.ErrnoException, written: Number, buffer: Buffer) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun write(fd: Number, data: Any, callback: ((err: NodeJS.ErrnoException, written: Number, str: String) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun write(fd: Number, data: Any, offset: Number, callback: ((err: NodeJS.ErrnoException, written: Number, str: String) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun write(fd: Number, data: Any, offset: Number, encoding: String, callback: ((err: NodeJS.ErrnoException, written: Number, str: String) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun writeSync(fd: Number, buffer: Buffer, offset: Number, length: Number, position: Number? = definedExternally /* null */): Number = definedExternally
external fun writeSync(fd: Number, data: Any, position: Number? = definedExternally /* null */, enconding: String? = definedExternally /* null */): Number = definedExternally
external fun read(fd: Number, buffer: Buffer, offset: Number, length: Number, position: Number?, callback: ((err: NodeJS.ErrnoException, bytesRead: Number, buffer: Buffer) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun readSync(fd: Number, buffer: Buffer, offset: Number, length: Number, position: Number?): Number = definedExternally
external fun readFile(filename: String, encoding: Nothing?, callback: (err: NodeJS.ErrnoException, data: Buffer) -> Unit): Unit = definedExternally
external fun readFile(filename: String, encoding: String, callback: (err: NodeJS.ErrnoException, data: String) -> Unit): Unit = definedExternally
external fun readFile(filename: String, encoding: String?, callback: (err: NodeJS.ErrnoException, data: dynamic /* String | Buffer */) -> Unit): Unit = definedExternally
external interface `T$21` {
    var encoding: Nothing?
    var flag: String? get() = definedExternally; set(value) = definedExternally
}
external fun readFile(filename: String, options: `T$21`, callback: (err: NodeJS.ErrnoException, data: Buffer) -> Unit): Unit = definedExternally
external interface `T$22` {
    var encoding: String
    var flag: String? get() = definedExternally; set(value) = definedExternally
}
external fun readFile(filename: String, options: `T$22`, callback: (err: NodeJS.ErrnoException, data: String) -> Unit): Unit = definedExternally
external interface `T$23` {
    var encoding: String?
    var flag: String? get() = definedExternally; set(value) = definedExternally
}
external fun readFile(filename: String, options: `T$23`, callback: (err: NodeJS.ErrnoException, data: dynamic /* String | Buffer */) -> Unit): Unit = definedExternally
external interface `T$24` {
    var flag: String? get() = definedExternally; set(value) = definedExternally
}
external fun readFile(filename: String, options: `T$24`, callback: (err: NodeJS.ErrnoException, data: Buffer) -> Unit): Unit = definedExternally
external fun readFile(filename: String, callback: (err: NodeJS.ErrnoException, data: Buffer) -> Unit): Unit = definedExternally
external fun readFileSync(filename: String, encoding: Nothing?): Buffer = definedExternally
external fun readFileSync(filename: String, encoding: String): String = definedExternally
external fun readFileSync(filename: String, encoding: String?): dynamic /* String | Buffer */ = definedExternally
external fun readFileSync(filename: String, options: `T$21`): Buffer = definedExternally
external fun readFileSync(filename: String, options: `T$22`): String = definedExternally
external fun readFileSync(filename: String, options: `T$23`): dynamic /* String | Buffer */ = definedExternally
external fun readFileSync(filename: String, options: `T$24`? = definedExternally /* null */): Buffer = definedExternally
external fun writeFile(filename: String, data: Any, callback: ((err: NodeJS.ErrnoException) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun writeFile(filename: String, data: Any, encoding: String, callback: (err: NodeJS.ErrnoException) -> Unit): Unit = definedExternally
external interface `T$25` {
    var encoding: String? get() = definedExternally; set(value) = definedExternally
    var mode: Number? get() = definedExternally; set(value) = definedExternally
    var flag: String? get() = definedExternally; set(value) = definedExternally
}
external fun writeFile(filename: String, data: Any, options: `T$25`, callback: ((err: NodeJS.ErrnoException) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external interface `T$26` {
    var encoding: String? get() = definedExternally; set(value) = definedExternally
    var mode: String? get() = definedExternally; set(value) = definedExternally
    var flag: String? get() = definedExternally; set(value) = definedExternally
}
external fun writeFile(filename: String, data: Any, options: `T$26`, callback: ((err: NodeJS.ErrnoException) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun writeFileSync(filename: String, data: Any, encoding: String): Unit = definedExternally
external fun writeFileSync(filename: String, data: Any, options: `T$25`? = definedExternally /* null */): Unit = definedExternally
external fun writeFileSync(filename: String, data: Any, options: `T$26`? = definedExternally /* null */): Unit = definedExternally
external fun appendFile(filename: String, data: Any, encoding: String, callback: (err: NodeJS.ErrnoException) -> Unit): Unit = definedExternally
external fun appendFile(filename: String, data: Any, options: `T$25`, callback: ((err: NodeJS.ErrnoException) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun appendFile(filename: String, data: Any, options: `T$26`, callback: ((err: NodeJS.ErrnoException) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun appendFile(filename: String, data: Any, callback: ((err: NodeJS.ErrnoException) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun appendFileSync(filename: String, data: Any, encoding: String): Unit = definedExternally
external fun appendFileSync(filename: String, data: Any, options: `T$25`? = definedExternally /* null */): Unit = definedExternally
external fun appendFileSync(filename: String, data: Any, options: `T$26`? = definedExternally /* null */): Unit = definedExternally
external fun watchFile(filename: String, listener: (curr: Stats, prev: Stats) -> Unit): Unit = definedExternally
external interface `T$27` {
    var persistent: Boolean? get() = definedExternally; set(value) = definedExternally
    var interval: Number? get() = definedExternally; set(value) = definedExternally
}
external fun watchFile(filename: String, options: `T$27`, listener: (curr: Stats, prev: Stats) -> Unit): Unit = definedExternally
external fun unwatchFile(filename: String, listener: ((curr: Stats, prev: Stats) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun watch(filename: String, listener: ((event: String, filename: String) -> Any)? = definedExternally /* null */): FSWatcher = definedExternally
external fun watch(filename: String, encoding: String, listener: ((event: String, filename: dynamic /* String | Buffer */) -> Any)? = definedExternally /* null */): FSWatcher = definedExternally
external interface `T$28` {
    var persistent: Boolean? get() = definedExternally; set(value) = definedExternally
    var recursive: Boolean? get() = definedExternally; set(value) = definedExternally
    var encoding: String? get() = definedExternally; set(value) = definedExternally
}
external fun watch(filename: String, options: `T$28`, listener: ((event: String, filename: dynamic /* String | Buffer */) -> Any)? = definedExternally /* null */): FSWatcher = definedExternally
external fun exists(path: String, callback: ((exists: Boolean) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun exists(path: Buffer, callback: ((exists: Boolean) -> Unit)? = definedExternally /* null */): Unit = definedExternally
external fun existsSync(path: String): Boolean = definedExternally
external fun existsSync(path: Buffer): Boolean = definedExternally
external fun access(path: String, callback: (err: NodeJS.ErrnoException) -> Unit): Unit = definedExternally
external fun access(path: Buffer, callback: (err: NodeJS.ErrnoException) -> Unit): Unit = definedExternally
external fun access(path: String, mode: Number, callback: (err: NodeJS.ErrnoException) -> Unit): Unit = definedExternally
external fun access(path: Buffer, mode: Number, callback: (err: NodeJS.ErrnoException) -> Unit): Unit = definedExternally
external fun accessSync(path: String, mode: Number? = definedExternally /* null */): Unit = definedExternally
external fun accessSync(path: Buffer, mode: Number? = definedExternally /* null */): Unit = definedExternally
external interface `T$29` {
    var flags: String? get() = definedExternally; set(value) = definedExternally
    var encoding: String? get() = definedExternally; set(value) = definedExternally
    var fd: Number? get() = definedExternally; set(value) = definedExternally
    var mode: Number? get() = definedExternally; set(value) = definedExternally
    var autoClose: Boolean? get() = definedExternally; set(value) = definedExternally
    var start: Number? get() = definedExternally; set(value) = definedExternally
    var end: Number? get() = definedExternally; set(value) = definedExternally
    var highWaterMark: Number? get() = definedExternally; set(value) = definedExternally
}
external fun createReadStream(path: String, options: `T$29`? = definedExternally /* null */): ReadStream = definedExternally
external fun createReadStream(path: Buffer, options: `T$29`? = definedExternally /* null */): ReadStream = definedExternally
external interface `T$30` {
    var flags: String? get() = definedExternally; set(value) = definedExternally
    var encoding: String? get() = definedExternally; set(value) = definedExternally
    var fd: Number? get() = definedExternally; set(value) = definedExternally
    var mode: Number? get() = definedExternally; set(value) = definedExternally
    var autoClose: Boolean? get() = definedExternally; set(value) = definedExternally
    var start: Number? get() = definedExternally; set(value) = definedExternally
}
external fun createWriteStream(path: String, options: `T$30`? = definedExternally /* null */): WriteStream = definedExternally
external fun createWriteStream(path: Buffer, options: `T$30`? = definedExternally /* null */): WriteStream = definedExternally
external fun fdatasync(fd: Number, callback: Function<*>): Unit = definedExternally
external fun fdatasyncSync(fd: Number): Unit = definedExternally
external fun mkdirSync(path: String): Unit = definedExternally
external fun mkdirSync(path: Buffer): Unit = definedExternally
external fun readdirSync(path: String): Array<String> = definedExternally
external fun readdirSync(path: Buffer): Array<String> = definedExternally
external fun writeFileSync(filename: String, data: Any): Unit = definedExternally
external fun appendFileSync(filename: String, data: Any): Unit = definedExternally
