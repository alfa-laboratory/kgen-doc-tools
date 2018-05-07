@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("dns")
package dns

external class NodeJS {
    class ErrnoException
}

external var ADDRCONFIG: Number = definedExternally
external var V4MAPPED: Number = definedExternally
external interface LookupOptions {
    var family: Number? get() = definedExternally; set(value) = definedExternally
    var hints: Number? get() = definedExternally; set(value) = definedExternally
    var all: Boolean? get() = definedExternally; set(value) = definedExternally
}
external interface LookupOneOptions : LookupOptions {
    override var all: Boolean? get() = definedExternally; set(value) = definedExternally
}
external interface LookupAllOptions : LookupOptions {
    override var all: Boolean?
}
external interface LookupAddress {
    var address: String
    var family: Number
}
external fun lookup(hostname: String, family: Number, callback: (err: NodeJS.ErrnoException, address: String, family: Number) -> Unit): Unit = definedExternally
external fun lookup(hostname: String, options: LookupOneOptions, callback: (err: NodeJS.ErrnoException, address: String, family: Number) -> Unit): Unit = definedExternally
external fun lookup(hostname: String, options: LookupAllOptions, callback: (err: NodeJS.ErrnoException, addresses: Array<LookupAddress>) -> Unit): Unit = definedExternally
external fun lookup(hostname: String, options: LookupOptions, callback: (err: NodeJS.ErrnoException, address: dynamic /* String | Array<LookupAddress> */, family: Number) -> Unit): Unit = definedExternally
external fun lookup(hostname: String, callback: (err: NodeJS.ErrnoException, address: String, family: Number) -> Unit): Unit = definedExternally
external interface ResolveOptions {
    var ttl: Boolean
}
external interface ResolveWithTtlOptions : ResolveOptions {
    override var ttl: Boolean
}
external interface RecordWithTtl {
    var address: String
    var ttl: Number
}
external interface MxRecord {
    var priority: Number
    var exchange: String
}
external interface NaptrRecord {
    var flags: String
    var service: String
    var regexp: String
    var replacement: String
    var order: Number
    var preference: Number
}
external interface SoaRecord {
    var nsname: String
    var hostmaster: String
    var serial: Number
    var refresh: Number
    var retry: Number
    var expire: Number
    var minttl: Number
}
external interface SrvRecord {
    var priority: Number
    var weight: Number
    var port: Number
    var name: String
}
external fun resolve(hostname: String, callback: (err: NodeJS.ErrnoException, addresses: Array<String>) -> Unit): Unit = definedExternally
external fun resolve(hostname: String, rrtype: String /* "A" */, callback: (err: NodeJS.ErrnoException, addresses: Array<String>) -> Unit): Unit = definedExternally
external fun resolve(hostname: String, rrtype: String /* "AAAA" */, callback: (err: NodeJS.ErrnoException, addresses: Array<String>) -> Unit): Unit = definedExternally
external fun resolve(hostname: String, rrtype: String /* "CNAME" */, callback: (err: NodeJS.ErrnoException, addresses: Array<String>) -> Unit): Unit = definedExternally
external fun resolve(hostname: String, rrtype: String /* "MX" */, callback: (err: NodeJS.ErrnoException, addresses: Array<MxRecord>) -> Unit): Unit = definedExternally
external fun resolve(hostname: String, rrtype: String /* "NAPTR" */, callback: (err: NodeJS.ErrnoException, addresses: Array<NaptrRecord>) -> Unit): Unit = definedExternally
external fun resolve(hostname: String, rrtype: String /* "NS" */, callback: (err: NodeJS.ErrnoException, addresses: Array<String>) -> Unit): Unit = definedExternally
external fun resolve(hostname: String, rrtype: String /* "PTR" */, callback: (err: NodeJS.ErrnoException, addresses: Array<String>) -> Unit): Unit = definedExternally
external fun resolve(hostname: String, rrtype: String /* "SOA" */, callback: (err: NodeJS.ErrnoException, addresses: SoaRecord) -> Unit): Unit = definedExternally
external fun resolve(hostname: String, rrtype: String /* "SRV" */, callback: (err: NodeJS.ErrnoException, addresses: Array<SrvRecord>) -> Unit): Unit = definedExternally
external fun resolve(hostname: String, rrtype: String /* "TXT" */, callback: (err: NodeJS.ErrnoException, addresses: Array<Array<String>>) -> Unit): Unit = definedExternally
external fun resolve(hostname: String, rrtype: String, callback: (err: NodeJS.ErrnoException, addresses: dynamic /* Array<String> | Array<MxRecord> | Array<NaptrRecord> | SoaRecord | Array<SrvRecord> | Array<Array<String>> */) -> Unit): Unit = definedExternally
external fun resolve4(hostname: String, callback: (err: NodeJS.ErrnoException, addresses: Array<String>) -> Unit): Unit = definedExternally
external fun resolve4(hostname: String, options: ResolveWithTtlOptions, callback: (err: NodeJS.ErrnoException, addresses: Array<RecordWithTtl>) -> Unit): Unit = definedExternally
external fun resolve4(hostname: String, options: ResolveOptions, callback: (err: NodeJS.ErrnoException, addresses: dynamic /* Array<String> | Array<RecordWithTtl> */) -> Unit): Unit = definedExternally
external fun resolve6(hostname: String, callback: (err: NodeJS.ErrnoException, addresses: Array<String>) -> Unit): Unit = definedExternally
external fun resolve6(hostname: String, options: ResolveWithTtlOptions, callback: (err: NodeJS.ErrnoException, addresses: Array<RecordWithTtl>) -> Unit): Unit = definedExternally
external fun resolve6(hostname: String, options: ResolveOptions, callback: (err: NodeJS.ErrnoException, addresses: dynamic /* Array<String> | Array<RecordWithTtl> */) -> Unit): Unit = definedExternally
external fun resolveCname(hostname: String, callback: (err: NodeJS.ErrnoException, addresses: Array<String>) -> Unit): Unit = definedExternally
external fun resolveMx(hostname: String, callback: (err: NodeJS.ErrnoException, addresses: Array<MxRecord>) -> Unit): Unit = definedExternally
external fun resolveNaptr(hostname: String, callback: (err: NodeJS.ErrnoException, addresses: Array<NaptrRecord>) -> Unit): Unit = definedExternally
external fun resolveNs(hostname: String, callback: (err: NodeJS.ErrnoException, addresses: Array<String>) -> Unit): Unit = definedExternally
external fun resolvePtr(hostname: String, callback: (err: NodeJS.ErrnoException, addresses: Array<String>) -> Unit): Unit = definedExternally
external fun resolveSoa(hostname: String, callback: (err: NodeJS.ErrnoException, address: SoaRecord) -> Unit): Unit = definedExternally
external fun resolveSrv(hostname: String, callback: (err: NodeJS.ErrnoException, addresses: Array<SrvRecord>) -> Unit): Unit = definedExternally
external fun resolveTxt(hostname: String, callback: (err: NodeJS.ErrnoException, addresses: Array<Array<String>>) -> Unit): Unit = definedExternally
external fun reverse(ip: String, callback: (err: NodeJS.ErrnoException, hostnames: Array<String>) -> Unit): Unit = definedExternally
external fun setServers(servers: Array<String>): Unit = definedExternally
external var NODATA: String = definedExternally
external var FORMERR: String = definedExternally
external var SERVFAIL: String = definedExternally
external var NOTFOUND: String = definedExternally
external var NOTIMP: String = definedExternally
external var REFUSED: String = definedExternally
external var BADQUERY: String = definedExternally
external var BADNAME: String = definedExternally
external var BADFAMILY: String = definedExternally
external var BADRESP: String = definedExternally
external var CONNREFUSED: String = definedExternally
external var TIMEOUT: String = definedExternally
external var EOF: String = definedExternally
external var FILE: String = definedExternally
external var NOMEM: String = definedExternally
external var DESTRUCTION: String = definedExternally
external var BADSTR: String = definedExternally
external var BADFLAGS: String = definedExternally
external var NONAME: String = definedExternally
external var BADHINTS: String = definedExternally
external var NOTINITIALIZED: String = definedExternally
external var LOADIPHLPAPI: String = definedExternally
external var ADDRGETNETWORKPARAMS: String = definedExternally
external var CANCELLED: String = definedExternally
