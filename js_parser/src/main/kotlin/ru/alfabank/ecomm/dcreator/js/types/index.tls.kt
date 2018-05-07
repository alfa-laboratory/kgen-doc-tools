//@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
//@file:JsModule("tls")
//package tls
//
//import buf.Buffer
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
//external var CLIENT_RENEG_LIMIT: Number = definedExternally
//external var CLIENT_RENEG_WINDOW: Number = definedExternally
//external interface Certificate {
//    var C: String
//    var ST: String
//    var L: String
//    var O: String
//    var OU: String
//    var CN: String
//}
//external interface `T$31` {
//    @nativeGetter
//    operator fun get(index: String): Array<String>?
//    @nativeSetter
//    operator fun set(index: String, value: Array<String>)
//}
//external interface PeerCertificate {
//    var subject: Certificate
//    var issuer: Certificate
//    var subjectaltname: String
//    var infoAccess: `T$31`
//    var modulus: String
//    var exponent: String
//    var valid_from: String
//    var valid_to: String
//    var fingerprint: String
//    var ext_key_usage: Array<String>
//    var serialNumber: String
//    var raw: Buffer
//}
//external interface DetailedPeerCertificate : PeerCertificate {
//    var issuerCertificate: DetailedPeerCertificate
//}
//external interface CipherNameAndProtocol {
//    var name: String
//    var version: String
//}
//external interface `T$32` {
//    var secureContext: SecureContext? get() = definedExternally; set(value) = definedExternally
//    var isServer: Boolean? get() = definedExternally; set(value) = definedExternally
//    var server: net.Server? get() = definedExternally; set(value) = definedExternally
//    var requestCert: Boolean? get() = definedExternally; set(value) = definedExternally
//    var rejectUnauthorized: Boolean? get() = definedExternally; set(value) = definedExternally
//    var NPNProtocols: dynamic /* Buffer | Array<String> */ get() = definedExternally; set(value) = definedExternally
//    var ALPNProtocols: dynamic /* Buffer | Array<String> */ get() = definedExternally; set(value) = definedExternally
//    var SNICallback: Function<*>? get() = definedExternally; set(value) = definedExternally
//    var session: Buffer? get() = definedExternally; set(value) = definedExternally
//    var requestOCSP: Boolean? get() = definedExternally; set(value) = definedExternally
//}
//external interface `T$33` {
//    var port: Number
//    var family: String
//    var address: String
//}
//external open class TLSSocket(socket: dynamic, options: `T$32`? = definedExternally /* null */) {
//    fun address(): `T$33` = definedExternally
//    open var authorized: Boolean = definedExternally
//    open var authorizationError: Error = definedExternally
//    open var encrypted: Boolean = definedExternally
//    open fun getCipher(): CipherNameAndProtocol = definedExternally
//    open fun getPeerCertificate(detailed: Boolean): DetailedPeerCertificate = definedExternally
//    open fun getPeerCertificate(detailed: Boolean? = definedExternally /* null */): PeerCertificate = definedExternally
//    open fun getPeerCertificate(detailed: Boolean? = definedExternally /* null */): dynamic /* PeerCertificate | DetailedPeerCertificate */ = definedExternally
//    open fun getProtocol(): String? = definedExternally
//    open fun getSession(): Any = definedExternally
//    open fun getTLSTicket(): Any = definedExternally
//    override var localAddress: String = definedExternally
//    override var localPort: Number = definedExternally
//    override var remoteAddress: String = definedExternally
//    override var remoteFamily: String = definedExternally
//    override var remotePort: Number = definedExternally
//    open fun renegotiate(options: TlsOptions, callback: (err: Error) -> Any): Any = definedExternally
//    open fun setMaxSendFragment(size: Number): Boolean = definedExternally
//    override fun addListener(event: String, listener: Function<*>): TLSSocket /* this */ = definedExternally
//    open fun addListener(event: String /* "OCSPResponse" */, listener: (response: Buffer) -> Unit): TLSSocket /* this */ = definedExternally
//    open fun addListener(event: String /* "secureConnect" */, listener: () -> Unit): TLSSocket /* this */ = definedExternally
//    override fun emit(event: String, vararg args: Any): Boolean = definedExternally
//    override fun emit(event: Any?, vararg args: Any): Boolean = definedExternally
//    open fun emit(event: String /* "OCSPResponse" */, response: Buffer): Boolean = definedExternally
//    open fun emit(event: String /* "secureConnect" */): Boolean = definedExternally
//    override fun on(event: String, listener: Function<*>): TLSSocket /* this */ = definedExternally
//    open fun on(event: String /* "OCSPResponse" */, listener: (response: Buffer) -> Unit): TLSSocket /* this */ = definedExternally
//    open fun on(event: String /* "secureConnect" */, listener: () -> Unit): TLSSocket /* this */ = definedExternally
//    override fun once(event: String, listener: Function<*>): TLSSocket /* this */ = definedExternally
//    open fun once(event: String /* "OCSPResponse" */, listener: (response: Buffer) -> Unit): TLSSocket /* this */ = definedExternally
//    open fun once(event: String /* "secureConnect" */, listener: () -> Unit): TLSSocket /* this */ = definedExternally
//    override fun prependListener(event: String, listener: Function<*>): TLSSocket /* this */ = definedExternally
//    open fun prependListener(event: String /* "OCSPResponse" */, listener: (response: Buffer) -> Unit): TLSSocket /* this */ = definedExternally
//    open fun prependListener(event: String /* "secureConnect" */, listener: () -> Unit): TLSSocket /* this */ = definedExternally
//    override fun prependOnceListener(event: String, listener: Function<*>): TLSSocket /* this */ = definedExternally
//    open fun prependOnceListener(event: String /* "OCSPResponse" */, listener: (response: Buffer) -> Unit): TLSSocket /* this */ = definedExternally
//    open fun prependOnceListener(event: String /* "secureConnect" */, listener: () -> Unit): TLSSocket /* this */ = definedExternally
//}
//external interface TlsOptions {
//    var host: String? get() = definedExternally; set(value) = definedExternally
//    var port: Number? get() = definedExternally; set(value) = definedExternally
//    var pfx: dynamic /* String | Array<Buffer> */ get() = definedExternally; set(value) = definedExternally
//    var key: dynamic /* String | Array<Any> | Buffer | Array<String> */ get() = definedExternally; set(value) = definedExternally
//    var passphrase: String? get() = definedExternally; set(value) = definedExternally
//    var cert: dynamic /* String | Buffer | Array<Buffer> | Array<String> */ get() = definedExternally; set(value) = definedExternally
//    var ca: dynamic /* String | Buffer | Array<Buffer> | Array<String> */ get() = definedExternally; set(value) = definedExternally
//    var crl: dynamic /* String | Array<String> | Nothing? */ get() = definedExternally; set(value) = definedExternally
//    var ciphers: String? get() = definedExternally; set(value) = definedExternally
//    var honorCipherOrder: Boolean? get() = definedExternally; set(value) = definedExternally
//    var requestCert: Boolean? get() = definedExternally; set(value) = definedExternally
//    var rejectUnauthorized: Boolean? get() = definedExternally; set(value) = definedExternally
//    var NPNProtocols: dynamic /* Buffer | Array<String> */ get() = definedExternally; set(value) = definedExternally
//    var SNICallback: ((servername: String, cb: (err: Error, ctx: SecureContext) -> Any) -> Any)? get() = definedExternally; set(value) = definedExternally
//    var ecdhCurve: String? get() = definedExternally; set(value) = definedExternally
//    var dhparam: dynamic /* String | Buffer */ get() = definedExternally; set(value) = definedExternally
//    var handshakeTimeout: Number? get() = definedExternally; set(value) = definedExternally
//    var ALPNProtocols: dynamic /* Buffer | Array<String> */ get() = definedExternally; set(value) = definedExternally
//    var sessionTimeout: Number? get() = definedExternally; set(value) = definedExternally
//    var ticketKeys: Any? get() = definedExternally; set(value) = definedExternally
//    var sessionIdContext: String? get() = definedExternally; set(value) = definedExternally
//    var secureProtocol: String? get() = definedExternally; set(value) = definedExternally
//}
//external interface ConnectionOptions {
//    var host: String? get() = definedExternally; set(value) = definedExternally
//    var port: Number? get() = definedExternally; set(value) = definedExternally
//    var socket: dynamic? get() = definedExternally; set(value) = definedExternally
//    var pfx: dynamic /* String | Buffer */ get() = definedExternally; set(value) = definedExternally
//    var key: dynamic /* String | Buffer | Array<Buffer> | Array<String> */ get() = definedExternally; set(value) = definedExternally
//    var passphrase: String? get() = definedExternally; set(value) = definedExternally
//    var cert: dynamic /* String | Buffer | Array<Buffer> | Array<String> */ get() = definedExternally; set(value) = definedExternally
//    var ca: dynamic /* String | Buffer | Array<dynamic /* String | Buffer */> */ get() = definedExternally; set(value) = definedExternally
//    var rejectUnauthorized: Boolean? get() = definedExternally; set(value) = definedExternally
//    var NPNProtocols: Array<dynamic /* String | Buffer */>? get() = definedExternally; set(value) = definedExternally
//    var servername: String? get() = definedExternally; set(value) = definedExternally
//    var path: String? get() = definedExternally; set(value) = definedExternally
//    var ALPNProtocols: Array<dynamic /* String | Buffer */>? get() = definedExternally; set(value) = definedExternally
//    var checkServerIdentity: ((servername: String, cert: dynamic /* String | Buffer | Array<dynamic /* String | Buffer */> */) -> Any)? get() = definedExternally; set(value) = definedExternally
//    var secureProtocol: String? get() = definedExternally; set(value) = definedExternally
//    var secureContext: Any? get() = definedExternally; set(value) = definedExternally
//    var session: Buffer? get() = definedExternally; set(value) = definedExternally
//    var minDHSize: Number? get() = definedExternally; set(value) = definedExternally
//}
//external interface `T$34` {
//    var key: String
//    var cert: String
//    var ca: String
//}
//external interface Server : net.Server {
//    override fun close(callback: Function<*>?): Server
//    override fun address(): `T$33`
//    fun addContext(hostName: String, credentials: `T$34`)
//    override var maxConnections: Number
//    override var connections: Number
//    override fun addListener(event: String, listener: Function<*>): Server /* this */
//    fun addListener(event: String /* "tlsClientError" */, listener: (err: Error, tlsSocket: TLSSocket) -> Unit): Server /* this */
//    fun addListener(event: String /* "newSession" */, listener: (sessionId: Any, sessionData: Any, callback: (err: Error, resp: Buffer) -> Unit) -> Unit): Server /* this */
//    fun addListener(event: String /* "OCSPRequest" */, listener: (certificate: Buffer, issuer: Buffer, callback: Function<*>) -> Unit): Server /* this */
//    fun addListener(event: String /* "resumeSession" */, listener: (sessionId: Any, callback: (err: Error, sessionData: Any) -> Unit) -> Unit): Server /* this */
//    fun addListener(event: String /* "secureConnection" */, listener: (tlsSocket: TLSSocket) -> Unit): Server /* this */
//    override fun emit(event: String, vararg args: Any): Boolean
//    override fun emit(event: Any?, vararg args: Any): Boolean
//    fun emit(event: String /* "tlsClientError" */, err: Error, tlsSocket: TLSSocket): Boolean
//    fun emit(event: String /* "newSession" */, sessionId: Any, sessionData: Any, callback: (err: Error, resp: Buffer) -> Unit): Boolean
//    fun emit(event: String /* "OCSPRequest" */, certificate: Buffer, issuer: Buffer, callback: Function<*>): Boolean
//    fun emit(event: String /* "resumeSession" */, sessionId: Any, callback: (err: Error, sessionData: Any) -> Unit): Boolean
//    fun emit(event: String /* "secureConnection" */, tlsSocket: TLSSocket): Boolean
//    override fun on(event: String, listener: Function<*>): Server /* this */
//    fun on(event: String /* "tlsClientError" */, listener: (err: Error, tlsSocket: TLSSocket) -> Unit): Server /* this */
//    fun on(event: String /* "newSession" */, listener: (sessionId: Any, sessionData: Any, callback: (err: Error, resp: Buffer) -> Unit) -> Unit): Server /* this */
//    fun on(event: String /* "OCSPRequest" */, listener: (certificate: Buffer, issuer: Buffer, callback: Function<*>) -> Unit): Server /* this */
//    fun on(event: String /* "resumeSession" */, listener: (sessionId: Any, callback: (err: Error, sessionData: Any) -> Unit) -> Unit): Server /* this */
//    fun on(event: String /* "secureConnection" */, listener: (tlsSocket: TLSSocket) -> Unit): Server /* this */
//    override fun once(event: String, listener: Function<*>): Server /* this */
//    fun once(event: String /* "tlsClientError" */, listener: (err: Error, tlsSocket: TLSSocket) -> Unit): Server /* this */
//    fun once(event: String /* "newSession" */, listener: (sessionId: Any, sessionData: Any, callback: (err: Error, resp: Buffer) -> Unit) -> Unit): Server /* this */
//    fun once(event: String /* "OCSPRequest" */, listener: (certificate: Buffer, issuer: Buffer, callback: Function<*>) -> Unit): Server /* this */
//    fun once(event: String /* "resumeSession" */, listener: (sessionId: Any, callback: (err: Error, sessionData: Any) -> Unit) -> Unit): Server /* this */
//    fun once(event: String /* "secureConnection" */, listener: (tlsSocket: TLSSocket) -> Unit): Server /* this */
//    override fun prependListener(event: String, listener: Function<*>): Server /* this */
//    fun prependListener(event: String /* "tlsClientError" */, listener: (err: Error, tlsSocket: TLSSocket) -> Unit): Server /* this */
//    fun prependListener(event: String /* "newSession" */, listener: (sessionId: Any, sessionData: Any, callback: (err: Error, resp: Buffer) -> Unit) -> Unit): Server /* this */
//    fun prependListener(event: String /* "OCSPRequest" */, listener: (certificate: Buffer, issuer: Buffer, callback: Function<*>) -> Unit): Server /* this */
//    fun prependListener(event: String /* "resumeSession" */, listener: (sessionId: Any, callback: (err: Error, sessionData: Any) -> Unit) -> Unit): Server /* this */
//    fun prependListener(event: String /* "secureConnection" */, listener: (tlsSocket: TLSSocket) -> Unit): Server /* this */
//    override fun prependOnceListener(event: String, listener: Function<*>): Server /* this */
//    fun prependOnceListener(event: String /* "tlsClientError" */, listener: (err: Error, tlsSocket: TLSSocket) -> Unit): Server /* this */
//    fun prependOnceListener(event: String /* "newSession" */, listener: (sessionId: Any, sessionData: Any, callback: (err: Error, resp: Buffer) -> Unit) -> Unit): Server /* this */
//    fun prependOnceListener(event: String /* "OCSPRequest" */, listener: (certificate: Buffer, issuer: Buffer, callback: Function<*>) -> Unit): Server /* this */
//    fun prependOnceListener(event: String /* "resumeSession" */, listener: (sessionId: Any, callback: (err: Error, sessionData: Any) -> Unit) -> Unit): Server /* this */
//    fun prependOnceListener(event: String /* "secureConnection" */, listener: (tlsSocket: TLSSocket) -> Unit): Server /* this */
//}
//external interface `T$35` {
//    var name: String
//    var version: String
//}
//external interface ClearTextStream : stream.internal.Duplex {
//    var authorized: Boolean
//    var authorizationError: Error
//    fun getPeerCertificate(): Any
//    var getCipher: `T$35`
//    var address: `T$33`
//    var remoteAddress: String
//    var remotePort: Number
//}
//external interface SecurePair {
//    var encrypted: Any
//    var cleartext: Any
//}
//external interface SecureContextOptions {
//    var pfx: dynamic /* String | Buffer */ get() = definedExternally; set(value) = definedExternally
//    var key: dynamic /* String | Buffer */ get() = definedExternally; set(value) = definedExternally
//    var passphrase: String? get() = definedExternally; set(value) = definedExternally
//    var cert: dynamic /* String | Buffer */ get() = definedExternally; set(value) = definedExternally
//    var ca: dynamic /* String | Buffer */ get() = definedExternally; set(value) = definedExternally
//    var crl: dynamic /* String | Array<String> | Nothing? */ get() = definedExternally; set(value) = definedExternally
//    var ciphers: String? get() = definedExternally; set(value) = definedExternally
//    var honorCipherOrder: Boolean? get() = definedExternally; set(value) = definedExternally
//}
//external interface SecureContext {
//    var context: Any
//}
//external fun createServer(options: TlsOptions, secureConnectionListener: ((socket: TLSSocket) -> Unit)? = definedExternally /* null */): Server = definedExternally
//external fun connect(options: ConnectionOptions, secureConnectionListener: (() -> Unit)? = definedExternally /* null */): TLSSocket = definedExternally
//external fun connect(port: Number, host: String? = definedExternally /* null */, options: ConnectionOptions? = definedExternally /* null */, secureConnectListener: (() -> Unit)? = definedExternally /* null */): TLSSocket = definedExternally
//external fun connect(port: Number, options: ConnectionOptions? = definedExternally /* null */, secureConnectListener: (() -> Unit)? = definedExternally /* null */): TLSSocket = definedExternally
//external fun createSecurePair(credentials: crypto.Credentials? = definedExternally /* null */, isServer: Boolean? = definedExternally /* null */, requestCert: Boolean? = definedExternally /* null */, rejectUnauthorized: Boolean? = definedExternally /* null */): SecurePair = definedExternally
//external fun createSecureContext(details: SecureContextOptions): SecureContext = definedExternally
//external fun connect(port: Number): TLSSocket = definedExternally
