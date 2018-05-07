@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("crypto")
package crypto

import buf.Buffer
import org.khronos.webgl.Uint8Array

external interface Certificate {
    fun exportChallenge(spkac: String): Buffer
    fun exportChallenge(spkac: Buffer): Buffer
    fun exportPublicKey(spkac: String): Buffer
    fun exportPublicKey(spkac: Buffer): Buffer
    fun verifySpkac(spkac: Buffer): Boolean
    companion object {
        @nativeInvoke
        operator fun invoke(): dynamic
    }
}
external var fips: Boolean = definedExternally
external interface CredentialDetails {
    var pfx: String
    var key: String
    var passphrase: String
    var cert: String
    var ca: dynamic /* String | Array<String> | Nothing? */
    var crl: dynamic /* String | Array<String> | Nothing? */
    var ciphers: String
}
external interface Credentials {
    var context: Any? get() = definedExternally; set(value) = definedExternally
}
external fun createCredentials(details: CredentialDetails): Credentials = definedExternally
external fun createHash(algorithm: String): Hash = definedExternally
external fun createHmac(algorithm: String, key: String): Hmac = definedExternally
external fun createHmac(algorithm: String, key: Buffer): Hmac = definedExternally
external interface Hash : NodeJS.ReadWriteStream {
    fun update(data: String): Hash
    fun update(data: Buffer): Hash
    fun update(data: String, input_encoding: String /* "ascii" */): Hash
    fun update(data: String, input_encoding: String /* "utf8" */): Hash
    fun update(data: String, input_encoding: String /* "latin1" */): Hash
    fun update(data: Buffer, input_encoding: String /* "ascii" */): Hash
    fun update(data: Buffer, input_encoding: String /* "utf8" */): Hash
    fun update(data: Buffer, input_encoding: String /* "latin1" */): Hash
    fun digest(): Buffer
    fun digest(encoding: String /* "base64" */): String
    fun digest(encoding: String /* "latin1" */): String
    fun digest(encoding: String /* "hex" */): String
}
external interface Hmac : NodeJS.ReadWriteStream {
    fun update(data: String): Hmac
    fun update(data: Buffer): Hmac
    fun update(data: String, input_encoding: String /* "ascii" */): Hmac
    fun update(data: String, input_encoding: String /* "utf8" */): Hmac
    fun update(data: String, input_encoding: String /* "latin1" */): Hmac
    fun update(data: Buffer, input_encoding: String /* "ascii" */): Hmac
    fun update(data: Buffer, input_encoding: String /* "utf8" */): Hmac
    fun update(data: Buffer, input_encoding: String /* "latin1" */): Hmac
    fun digest(): Buffer
    fun digest(encoding: String /* "base64" */): String
    fun digest(encoding: String /* "latin1" */): String
    fun digest(encoding: String /* "hex" */): String
}
external fun createCipher(algorithm: String, password: Any): Cipher = definedExternally
external fun createCipheriv(algorithm: String, key: Any, iv: Any): Cipher = definedExternally
external interface Cipher : NodeJS.ReadWriteStream {
    fun update(data: Buffer): Buffer
    fun update(data: String, input_encoding: String /* "ascii" */): Buffer
    fun update(data: String, input_encoding: String /* "utf8" */): Buffer
    fun update(data: String, input_encoding: String /* "binary" */): Buffer
    fun update(data: Buffer, input_encoding: Any, output_encoding: String /* "base64" */): String
    fun update(data: Buffer, input_encoding: Any, output_encoding: String /* "binary" */): String
    fun update(data: Buffer, input_encoding: Any, output_encoding: String /* "hex" */): String
    fun update(data: String, input_encoding: String /* "ascii" */, output_encoding: String /* "base64" */): String
    fun update(data: String, input_encoding: String /* "ascii" */, output_encoding: String /* "binary" */): String
    fun update(data: String, input_encoding: String /* "ascii" */, output_encoding: String /* "hex" */): String
    fun update(data: String, input_encoding: String /* "utf8" */, output_encoding: String /* "base64" */): String
    fun update(data: String, input_encoding: String /* "utf8" */, output_encoding: String /* "binary" */): String
    fun update(data: String, input_encoding: String /* "utf8" */, output_encoding: String /* "hex" */): String
    fun update(data: String, input_encoding: String /* "binary" */, output_encoding: String /* "base64" */): String
    fun update(data: String, input_encoding: String /* "binary" */, output_encoding: String /* "binary" */): String
    fun update(data: String, input_encoding: String /* "binary" */, output_encoding: String /* "hex" */): String
    fun final(): Buffer
    fun final(output_encoding: String): String
    fun setAutoPadding(auto_padding: Boolean? = definedExternally /* null */)
    fun getAuthTag(): Buffer
    fun setAAD(buffer: Buffer)
}
external fun createDecipher(algorithm: String, password: Any): Decipher = definedExternally
external fun createDecipheriv(algorithm: String, key: Any, iv: Any): Decipher = definedExternally
external interface Decipher : NodeJS.ReadWriteStream {
    fun update(data: Buffer): Buffer
    fun update(data: String, input_encoding: String /* "base64" */): Buffer
    fun update(data: String, input_encoding: String /* "binary" */): Buffer
    fun update(data: String, input_encoding: String /* "hex" */): Buffer
    fun update(data: Buffer, input_encoding: Any, output_encoding: String /* "ascii" */): String
    fun update(data: Buffer, input_encoding: Any, output_encoding: String /* "utf8" */): String
    fun update(data: Buffer, input_encoding: Any, output_encoding: String /* "binary" */): String
    fun update(data: String, input_encoding: String /* "base64" */, output_encoding: String /* "ascii" */): String
    fun update(data: String, input_encoding: String /* "base64" */, output_encoding: String /* "utf8" */): String
    fun update(data: String, input_encoding: String /* "base64" */, output_encoding: String /* "binary" */): String
    fun update(data: String, input_encoding: String /* "binary" */, output_encoding: String /* "ascii" */): String
    fun update(data: String, input_encoding: String /* "binary" */, output_encoding: String /* "utf8" */): String
    fun update(data: String, input_encoding: String /* "binary" */, output_encoding: String /* "binary" */): String
    fun update(data: String, input_encoding: String /* "hex" */, output_encoding: String /* "ascii" */): String
    fun update(data: String, input_encoding: String /* "hex" */, output_encoding: String /* "utf8" */): String
    fun update(data: String, input_encoding: String /* "hex" */, output_encoding: String /* "binary" */): String
    fun final(): Buffer
    fun final(output_encoding: String): String
    fun setAutoPadding(auto_padding: Boolean? = definedExternally /* null */)
    fun setAuthTag(tag: Buffer)
    fun setAAD(buffer: Buffer)
}
external fun createSign(algorithm: String): Signer = definedExternally
external interface `T$36` {
    var key: String
    var passphrase: String
}
external interface Signer : NodeJS.WritableStream {
    fun update(data: String): Signer
    fun update(data: Buffer): Signer
    fun update(data: String, input_encoding: String /* "ascii" */): Signer
    fun update(data: String, input_encoding: String /* "utf8" */): Signer
    fun update(data: String, input_encoding: String /* "latin1" */): Signer
    fun update(data: Buffer, input_encoding: String /* "ascii" */): Signer
    fun update(data: Buffer, input_encoding: String /* "utf8" */): Signer
    fun update(data: Buffer, input_encoding: String /* "latin1" */): Signer
    fun sign(private_key: String): Buffer
    fun sign(private_key: `T$36`): Buffer
    fun sign(private_key: String, output_format: String /* "base64" */): String
    fun sign(private_key: String, output_format: String /* "latin1" */): String
    fun sign(private_key: String, output_format: String /* "hex" */): String
    fun sign(private_key: `T$36`, output_format: String /* "base64" */): String
    fun sign(private_key: `T$36`, output_format: String /* "latin1" */): String
    fun sign(private_key: `T$36`, output_format: String /* "hex" */): String
}
external fun createVerify(algorith: String): Verify = definedExternally
external interface Verify : NodeJS.WritableStream {
    fun update(data: String): Verify
    fun update(data: Buffer): Verify
    fun update(data: String, input_encoding: String /* "ascii" */): Verify
    fun update(data: String, input_encoding: String /* "utf8" */): Verify
    fun update(data: String, input_encoding: String /* "latin1" */): Verify
    fun update(data: Buffer, input_encoding: String /* "ascii" */): Verify
    fun update(data: Buffer, input_encoding: String /* "utf8" */): Verify
    fun update(data: Buffer, input_encoding: String /* "latin1" */): Verify
    fun verify(`object`: String, signature: Buffer): Boolean
    fun verify(`object`: String, signature: String, signature_format: String /* "base64" */): Boolean
    fun verify(`object`: String, signature: String, signature_format: String /* "latin1" */): Boolean
    fun verify(`object`: String, signature: String, signature_format: String /* "hex" */): Boolean
}
external fun createDiffieHellman(prime_length: Number, generator: Number? = definedExternally /* null */): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: Buffer): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "base64" */): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "latin1" */): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "hex" */): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "base64" */, generator: Number): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "base64" */, generator: Buffer): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "latin1" */, generator: Number): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "latin1" */, generator: Buffer): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "hex" */, generator: Number): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "hex" */, generator: Buffer): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "base64" */, generator: String, generator_encoding: String /* "base64" */): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "base64" */, generator: String, generator_encoding: String /* "latin1" */): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "base64" */, generator: String, generator_encoding: String /* "hex" */): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "latin1" */, generator: String, generator_encoding: String /* "base64" */): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "latin1" */, generator: String, generator_encoding: String /* "latin1" */): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "latin1" */, generator: String, generator_encoding: String /* "hex" */): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "hex" */, generator: String, generator_encoding: String /* "base64" */): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "hex" */, generator: String, generator_encoding: String /* "latin1" */): DiffieHellman = definedExternally
external fun createDiffieHellman(prime: String, prime_encoding: String /* "hex" */, generator: String, generator_encoding: String /* "hex" */): DiffieHellman = definedExternally
external interface DiffieHellman {
    fun generateKeys(): Buffer
    fun generateKeys(encoding: String /* "base64" */): String
    fun generateKeys(encoding: String /* "latin1" */): String
    fun generateKeys(encoding: String /* "hex" */): String
    fun computeSecret(other_public_key: Buffer): Buffer
    fun computeSecret(other_public_key: String, input_encoding: String /* "base64" */): Buffer
    fun computeSecret(other_public_key: String, input_encoding: String /* "latin1" */): Buffer
    fun computeSecret(other_public_key: String, input_encoding: String /* "hex" */): Buffer
    fun computeSecret(other_public_key: String, input_encoding: String /* "base64" */, output_encoding: String /* "base64" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "base64" */, output_encoding: String /* "latin1" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "base64" */, output_encoding: String /* "hex" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "latin1" */, output_encoding: String /* "base64" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "latin1" */, output_encoding: String /* "latin1" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "latin1" */, output_encoding: String /* "hex" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "hex" */, output_encoding: String /* "base64" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "hex" */, output_encoding: String /* "latin1" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "hex" */, output_encoding: String /* "hex" */): String
    fun getPrime(): Buffer
    fun getPrime(encoding: String /* "base64" */): String
    fun getPrime(encoding: String /* "latin1" */): String
    fun getPrime(encoding: String /* "hex" */): String
    fun getGenerator(): Buffer
    fun getGenerator(encoding: String /* "base64" */): String
    fun getGenerator(encoding: String /* "latin1" */): String
    fun getGenerator(encoding: String /* "hex" */): String
    fun getPublicKey(): Buffer
    fun getPublicKey(encoding: String /* "base64" */): String
    fun getPublicKey(encoding: String /* "latin1" */): String
    fun getPublicKey(encoding: String /* "hex" */): String
    fun getPrivateKey(): Buffer
    fun getPrivateKey(encoding: String /* "base64" */): String
    fun getPrivateKey(encoding: String /* "latin1" */): String
    fun getPrivateKey(encoding: String /* "hex" */): String
    fun setPublicKey(public_key: Buffer)
    fun setPublicKey(public_key: String, encoding: String)
    fun setPrivateKey(private_key: Buffer)
    fun setPrivateKey(private_key: String, encoding: String)
    var verifyError: Number
}
external fun getDiffieHellman(group_name: String): DiffieHellman = definedExternally
external fun pbkdf2(password: String, salt: String, iterations: Number, keylen: Number, digest: String, callback: (err: Error, derivedKey: Buffer) -> Any): Unit = definedExternally
external fun pbkdf2(password: String, salt: Buffer, iterations: Number, keylen: Number, digest: String, callback: (err: Error, derivedKey: Buffer) -> Any): Unit = definedExternally
external fun pbkdf2(password: Buffer, salt: String, iterations: Number, keylen: Number, digest: String, callback: (err: Error, derivedKey: Buffer) -> Any): Unit = definedExternally
external fun pbkdf2(password: Buffer, salt: Buffer, iterations: Number, keylen: Number, digest: String, callback: (err: Error, derivedKey: Buffer) -> Any): Unit = definedExternally
external fun pbkdf2Sync(password: String, salt: String, iterations: Number, keylen: Number, digest: String): Buffer = definedExternally
external fun pbkdf2Sync(password: String, salt: Buffer, iterations: Number, keylen: Number, digest: String): Buffer = definedExternally
external fun pbkdf2Sync(password: Buffer, salt: String, iterations: Number, keylen: Number, digest: String): Buffer = definedExternally
external fun pbkdf2Sync(password: Buffer, salt: Buffer, iterations: Number, keylen: Number, digest: String): Buffer = definedExternally
external fun randomBytes(size: Number): Buffer = definedExternally
external fun randomBytes(size: Number, callback: (err: Error, buf: Buffer) -> Unit): Unit = definedExternally
external fun pseudoRandomBytes(size: Number): Buffer = definedExternally
external fun pseudoRandomBytes(size: Number, callback: (err: Error, buf: Buffer) -> Unit): Unit = definedExternally
external fun randomFillSync(buffer: Buffer, offset: Number? = definedExternally /* null */, size: Number? = definedExternally /* null */): Buffer = definedExternally
external fun randomFillSync(buffer: Uint8Array, offset: Number? = definedExternally /* null */, size: Number? = definedExternally /* null */): Buffer = definedExternally
external fun randomFill(buffer: Buffer, callback: (err: Error, buf: Buffer) -> Unit): Unit = definedExternally
external fun randomFill(buffer: Uint8Array, callback: (err: Error, buf: Uint8Array) -> Unit): Unit = definedExternally
external fun randomFill(buffer: Buffer, offset: Number, callback: (err: Error, buf: Buffer) -> Unit): Unit = definedExternally
external fun randomFill(buffer: Uint8Array, offset: Number, callback: (err: Error, buf: Uint8Array) -> Unit): Unit = definedExternally
external fun randomFill(buffer: Buffer, offset: Number, size: Number, callback: (err: Error, buf: Buffer) -> Unit): Unit = definedExternally
external fun randomFill(buffer: Uint8Array, offset: Number, size: Number, callback: (err: Error, buf: Uint8Array) -> Unit): Unit = definedExternally
external interface RsaPublicKey {
    var key: String
    var padding: Number? get() = definedExternally; set(value) = definedExternally
}
external interface RsaPrivateKey {
    var key: String
    var passphrase: String? get() = definedExternally; set(value) = definedExternally
    var padding: Number? get() = definedExternally; set(value) = definedExternally
}
external fun publicEncrypt(public_key: String, buffer: Buffer): Buffer = definedExternally
external fun publicEncrypt(public_key: RsaPublicKey, buffer: Buffer): Buffer = definedExternally
external fun privateDecrypt(private_key: String, buffer: Buffer): Buffer = definedExternally
external fun privateDecrypt(private_key: RsaPrivateKey, buffer: Buffer): Buffer = definedExternally
external fun privateEncrypt(private_key: String, buffer: Buffer): Buffer = definedExternally
external fun privateEncrypt(private_key: RsaPrivateKey, buffer: Buffer): Buffer = definedExternally
external fun publicDecrypt(public_key: String, buffer: Buffer): Buffer = definedExternally
external fun publicDecrypt(public_key: RsaPublicKey, buffer: Buffer): Buffer = definedExternally
external fun getCiphers(): Array<String> = definedExternally
external fun getCurves(): Array<String> = definedExternally
external fun getHashes(): Array<String> = definedExternally
external interface ECDH {
    fun generateKeys(): Buffer
    fun generateKeys(encoding: String /* "base64" */): String
    fun generateKeys(encoding: String /* "latin1" */): String
    fun generateKeys(encoding: String /* "hex" */): String
    fun generateKeys(encoding: String /* "base64" */, format: String /* "compressed" */): String
    fun generateKeys(encoding: String /* "base64" */, format: String /* "uncompressed" */): String
    fun generateKeys(encoding: String /* "base64" */, format: String /* "hybrid" */): String
    fun generateKeys(encoding: String /* "latin1" */, format: String /* "compressed" */): String
    fun generateKeys(encoding: String /* "latin1" */, format: String /* "uncompressed" */): String
    fun generateKeys(encoding: String /* "latin1" */, format: String /* "hybrid" */): String
    fun generateKeys(encoding: String /* "hex" */, format: String /* "compressed" */): String
    fun generateKeys(encoding: String /* "hex" */, format: String /* "uncompressed" */): String
    fun generateKeys(encoding: String /* "hex" */, format: String /* "hybrid" */): String
    fun computeSecret(other_public_key: Buffer): Buffer
    fun computeSecret(other_public_key: String, input_encoding: String /* "base64" */): Buffer
    fun computeSecret(other_public_key: String, input_encoding: String /* "latin1" */): Buffer
    fun computeSecret(other_public_key: String, input_encoding: String /* "hex" */): Buffer
    fun computeSecret(other_public_key: String, input_encoding: String /* "base64" */, output_encoding: String /* "base64" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "base64" */, output_encoding: String /* "latin1" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "base64" */, output_encoding: String /* "hex" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "latin1" */, output_encoding: String /* "base64" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "latin1" */, output_encoding: String /* "latin1" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "latin1" */, output_encoding: String /* "hex" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "hex" */, output_encoding: String /* "base64" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "hex" */, output_encoding: String /* "latin1" */): String
    fun computeSecret(other_public_key: String, input_encoding: String /* "hex" */, output_encoding: String /* "hex" */): String
    fun getPrivateKey(): Buffer
    fun getPrivateKey(encoding: String /* "base64" */): String
    fun getPrivateKey(encoding: String /* "latin1" */): String
    fun getPrivateKey(encoding: String /* "hex" */): String
    fun getPublicKey(): Buffer
    fun getPublicKey(encoding: String /* "base64" */): String
    fun getPublicKey(encoding: String /* "latin1" */): String
    fun getPublicKey(encoding: String /* "hex" */): String
    fun getPublicKey(encoding: String /* "base64" */, format: String /* "compressed" */): String
    fun getPublicKey(encoding: String /* "base64" */, format: String /* "uncompressed" */): String
    fun getPublicKey(encoding: String /* "base64" */, format: String /* "hybrid" */): String
    fun getPublicKey(encoding: String /* "latin1" */, format: String /* "compressed" */): String
    fun getPublicKey(encoding: String /* "latin1" */, format: String /* "uncompressed" */): String
    fun getPublicKey(encoding: String /* "latin1" */, format: String /* "hybrid" */): String
    fun getPublicKey(encoding: String /* "hex" */, format: String /* "compressed" */): String
    fun getPublicKey(encoding: String /* "hex" */, format: String /* "uncompressed" */): String
    fun getPublicKey(encoding: String /* "hex" */, format: String /* "hybrid" */): String
    fun setPrivateKey(private_key: Buffer)
    fun setPrivateKey(private_key: String, encoding: String /* "base64" */)
    fun setPrivateKey(private_key: String, encoding: String /* "latin1" */)
    fun setPrivateKey(private_key: String, encoding: String /* "hex" */)
}
external fun createECDH(curve_name: String): ECDH = definedExternally
external fun timingSafeEqual(a: Buffer, b: Buffer): Boolean = definedExternally
external var DEFAULT_ENCODING: String = definedExternally
