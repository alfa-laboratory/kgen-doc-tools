@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("os")
package os

external interface `T$9` {
    var user: Number
    var nice: Number
    var sys: Number
    var idle: Number
    var irq: Number
}
external interface CpuInfo {
    var model: String
    var speed: Number
    var times: `T$9`
}
external interface NetworkInterfaceInfo {
    var address: String
    var netmask: String
    var family: String
    var mac: String
    var internal: Boolean
}
external fun hostname(): String = definedExternally
external fun loadavg(): Array<Number> = definedExternally
external fun uptime(): Number = definedExternally
external fun freemem(): Number = definedExternally
external fun totalmem(): Number = definedExternally
external fun cpus(): Array<CpuInfo> = definedExternally
external fun type(): String = definedExternally
external fun release(): String = definedExternally
external interface `T$10` {
    @nativeGetter
    operator fun get(index: String): Array<NetworkInterfaceInfo>?
    @nativeSetter
    operator fun set(index: String, value: Array<NetworkInterfaceInfo>)
}
external fun networkInterfaces(): `T$10` = definedExternally
external fun homedir(): String = definedExternally
external interface `T$11` {
    var encoding: String
}
external interface `T$12` {
    var username: String
    var uid: Number
    var gid: Number
    var shell: Any
    var homedir: String
}
external fun userInfo(options: `T$11`? = definedExternally /* null */): `T$12` = definedExternally
external interface `T$13` {
    var SIGHUP: Number
    var SIGINT: Number
    var SIGQUIT: Number
    var SIGILL: Number
    var SIGTRAP: Number
    var SIGABRT: Number
    var SIGIOT: Number
    var SIGBUS: Number
    var SIGFPE: Number
    var SIGKILL: Number
    var SIGUSR1: Number
    var SIGSEGV: Number
    var SIGUSR2: Number
    var SIGPIPE: Number
    var SIGALRM: Number
    var SIGTERM: Number
    var SIGCHLD: Number
    var SIGSTKFLT: Number
    var SIGCONT: Number
    var SIGSTOP: Number
    var SIGTSTP: Number
    var SIGTTIN: Number
    var SIGTTOU: Number
    var SIGURG: Number
    var SIGXCPU: Number
    var SIGXFSZ: Number
    var SIGVTALRM: Number
    var SIGPROF: Number
    var SIGWINCH: Number
    var SIGIO: Number
    var SIGPOLL: Number
    var SIGPWR: Number
    var SIGSYS: Number
    var SIGUNUSED: Number
}
external interface `T$14` {
    var E2BIG: Number
    var EACCES: Number
    var EADDRINUSE: Number
    var EADDRNOTAVAIL: Number
    var EAFNOSUPPORT: Number
    var EAGAIN: Number
    var EALREADY: Number
    var EBADF: Number
    var EBADMSG: Number
    var EBUSY: Number
    var ECANCELED: Number
    var ECHILD: Number
    var ECONNABORTED: Number
    var ECONNREFUSED: Number
    var ECONNRESET: Number
    var EDEADLK: Number
    var EDESTADDRREQ: Number
    var EDOM: Number
    var EDQUOT: Number
    var EEXIST: Number
    var EFAULT: Number
    var EFBIG: Number
    var EHOSTUNREACH: Number
    var EIDRM: Number
    var EILSEQ: Number
    var EINPROGRESS: Number
    var EINTR: Number
    var EINVAL: Number
    var EIO: Number
    var EISCONN: Number
    var EISDIR: Number
    var ELOOP: Number
    var EMFILE: Number
    var EMLINK: Number
    var EMSGSIZE: Number
    var EMULTIHOP: Number
    var ENAMETOOLONG: Number
    var ENETDOWN: Number
    var ENETRESET: Number
    var ENETUNREACH: Number
    var ENFILE: Number
    var ENOBUFS: Number
    var ENODATA: Number
    var ENODEV: Number
    var ENOENT: Number
    var ENOEXEC: Number
    var ENOLCK: Number
    var ENOLINK: Number
    var ENOMEM: Number
    var ENOMSG: Number
    var ENOPROTOOPT: Number
    var ENOSPC: Number
    var ENOSR: Number
    var ENOSTR: Number
    var ENOSYS: Number
    var ENOTCONN: Number
    var ENOTDIR: Number
    var ENOTEMPTY: Number
    var ENOTSOCK: Number
    var ENOTSUP: Number
    var ENOTTY: Number
    var ENXIO: Number
    var EOPNOTSUPP: Number
    var EOVERFLOW: Number
    var EPERM: Number
    var EPIPE: Number
    var EPROTO: Number
    var EPROTONOSUPPORT: Number
    var EPROTOTYPE: Number
    var ERANGE: Number
    var EROFS: Number
    var ESPIPE: Number
    var ESRCH: Number
    var ESTALE: Number
    var ETIME: Number
    var ETIMEDOUT: Number
    var ETXTBSY: Number
    var EWOULDBLOCK: Number
    var EXDEV: Number
}
external object constants {
    var UV_UDP_REUSEADDR: Number = definedExternally
    var signals: `T$13` = definedExternally
    var errno: `T$14` = definedExternally
}
external fun arch(): String = definedExternally
external fun platform(): dynamic /* String /* "aix" */ | String /* "android" */ | String /* "darwin" */ | String /* "freebsd" */ | String /* "linux" */ | String /* "openbsd" */ | String /* "sunos" */ | String /* "win32" */ */ = definedExternally
external fun tmpdir(): String = definedExternally
external var EOL: String = definedExternally
external fun endianness(): dynamic /* String /* "BE" */ | String /* "LE" */ */ = definedExternally
