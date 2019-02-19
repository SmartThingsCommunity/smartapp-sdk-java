package app.service

import java.io.StringReader
import java.security.KeyFactory
import java.security.KeyPair
import java.security.spec.RSAPublicKeySpec

import io.ktor.request.*
import net.adamcin.httpsig.api.Authorization
import net.adamcin.httpsig.api.Challenge
import net.adamcin.httpsig.api.DefaultKeychain
import net.adamcin.httpsig.api.DefaultVerifier
import net.adamcin.httpsig.api.RequestContent;
import net.adamcin.httpsig.ssh.jce.KeyFormat
import net.adamcin.httpsig.ssh.jce.SSHKey
import net.adamcin.httpsig.ssh.jce.UserFingerprintKeyId
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.crypto.params.RSAKeyParameters
import org.bouncycastle.crypto.util.PublicKeyFactory
import org.bouncycastle.openssl.PEMParser
import org.slf4j.LoggerFactory


class HttpVerificationService {
    val log = LoggerFactory.getLogger(HttpVerificationService::class.java)

    private val publicKey: String?

    init {
        val publicKeyResource = HttpVerificationService::class.java.getResource("/smartthings_rsa.pub")
        if (publicKeyResource == null) {
            log.info("no public key yet; will only accept PING lifecycle requests")
            publicKey = null
        } else {
            publicKey = publicKeyResource.readText()
        }
    }

    fun verify(request: ApplicationRequest): Boolean {
        if (publicKey == null) {
            log.error("Public key file not set up properly. Please see README (Configure Public Key) for"
                    + " directions and don't forget to restart this server.")
            return false
        }

        val verified = verifyRequest(request)
        if (!verified) {
            log.error("Request not verified!")
        }
        return verified
    }

    private fun verifyRequest(request: ApplicationRequest): Boolean {
        val pair = getRSAKeyPair()
        val keychain = DefaultKeychain()
        val keyId = UserFingerprintKeyId("SmartThings")
        keychain.add(SSHKey(KeyFormat.SSH_RSA, pair))
        val verifier = DefaultVerifier(keychain, keyId)
        val headers = request.headers

        val authorization = Authorization.parse(headers.get("Authorization"))

        if (authorization == null) {
            log.error("Request contains no authorization header")
            return false
        }

        val challenge = Challenge("<preemptive>", authorization.headers, listOf(authorization.algorithm))

        val signedHeaders = authorization.headers.map(String::toLowerCase).toSet()

        log.debug("path: " + request.path())
        log.debug("requestURI: " + request.uri)

        val content = RequestContent.Builder()
            .setRequestTarget(request.httpMethod.value, request.uri)

        headers.names().forEach { headerName ->
            if (signedHeaders.contains(headerName.toLowerCase())) {
                content.addHeader(headerName, headers.get(headerName))
            }
        }

        return verifier.verify(challenge, content.build(), authorization)
    }

    private fun getRSAKeyPair(): KeyPair {
        val pk = PEMParser(StringReader(publicKey)).use { parser ->
            val info = parser.readObject() as SubjectPublicKeyInfo
            val param = PublicKeyFactory.createKey(info) as RSAKeyParameters
            val spec = RSAPublicKeySpec(param.modulus, param.exponent)
            KeyFactory.getInstance("RSA").generatePublic(spec)
        }
        return KeyPair(pk, null)
    }
}
