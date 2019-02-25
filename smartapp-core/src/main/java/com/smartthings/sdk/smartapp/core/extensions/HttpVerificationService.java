package com.smartthings.sdk.smartapp.core.extensions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.openssl.PEMParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.adamcin.httpsig.api.Authorization;
import net.adamcin.httpsig.api.Challenge;
import net.adamcin.httpsig.api.DefaultKeychain;
import net.adamcin.httpsig.api.DefaultVerifier;
import net.adamcin.httpsig.api.KeyId;
import net.adamcin.httpsig.api.RequestContent;
import net.adamcin.httpsig.api.Verifier;
import net.adamcin.httpsig.ssh.jce.KeyFormat;
import net.adamcin.httpsig.ssh.jce.SSHKey;
import net.adamcin.httpsig.ssh.jce.UserFingerprintKeyId;


/**
 * All requests should have their HTTP signature verified to ensure if request is actually from SmartThings.
 * This class has the implementation of signature authentication and verification
 * from https://github.com/adamcin/httpsig-java.
 * The public key that is generated when registering a smartapp; is used to verify the request.
 */
public class HttpVerificationService {
    private static final Logger LOG = LoggerFactory.getLogger(HttpVerificationService.class);

    private final String publicKey;

    public HttpVerificationService() {
        this("/smartthings_rsa.pub");
    }

    public HttpVerificationService(String publicKeyPath) {
        InputStream in = this.getClass().getResourceAsStream(publicKeyPath);
        if (in == null) {
            // This is not an error until we are past the PING step.
            LOG.info("no public key yet; will only accept PING lifecycle requests");
            publicKey = null;
            return;
        }

        LOG.debug("Looking for public key file: smartthings_rsa.pub");
        publicKey = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));
    }

    public boolean verify(String method, String uri, Map<String, String> headers) {
        if (publicKey == null) {
            LOG.error("Public key file not set up properly. Please see README (Configure Public Key) for"
                    + " directions and don't forget to restart this server.");
            return false;
        }
        boolean verified = verifyRequest(method, uri, headers);
        if (!verified) {
            LOG.error("Request not verified!");
        }
        return verified;
    }

    private boolean verifyRequest(String method, String uri, Map<String, String> headers) {
        KeyPair pair = getRSAKeyPair();
        DefaultKeychain keychain = new DefaultKeychain();
        KeyId keyId = new UserFingerprintKeyId("SmartThings");
        keychain.add(new SSHKey(KeyFormat.SSH_RSA, pair));
        Verifier verifier = new DefaultVerifier(keychain, keyId);

        String authorizationHeader = headers.get("Authorization");
        if (authorizationHeader == null) {
            // Spring lower-cases header names
            authorizationHeader = headers.get("authorization");
        }
        Authorization authorization = Authorization.parse(authorizationHeader);

        if (authorization == null) {
            LOG.error("Request contains no authorization header");
            return false;
        }

        Challenge challenge = new Challenge("<preemptive>", authorization.getHeaders(),
            Collections.unmodifiableList(Arrays.asList(authorization.getAlgorithm())));

        Set<String> signedHeaders = authorization.getHeaders().stream()
            .map(String::toLowerCase)
            .collect(Collectors.toSet());

        LOG.debug("requestURI: " + uri);

        RequestContent.Builder content = new RequestContent.Builder()
            .setRequestTarget(method, uri);

        headers.keySet().stream()
            .filter(headerName -> signedHeaders.contains(headerName.toLowerCase()))
            .forEach(headerName -> content.addHeader(headerName, headers.get(headerName)));

        return verifier.verify(challenge, content.build(), authorization);
    }

    private KeyPair getRSAKeyPair() {
        PublicKey publicKey = getPublicKey();
        return new KeyPair(publicKey, null);
    }

    private PublicKey getPublicKey() {
        try (PEMParser parser = new PEMParser(new StringReader(publicKey))) {
            SubjectPublicKeyInfo info = (SubjectPublicKeyInfo) parser.readObject();
            RSAKeyParameters param = (RSAKeyParameters) PublicKeyFactory.createKey(info);
            RSAPublicKeySpec spec = new RSAPublicKeySpec(param.getModulus(), param.getExponent());
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (IOException e) {
            LOG.error("create_public_key_failed", e);
            throw new RuntimeException("Unable to create public key from PEM");
        } catch (NoSuchAlgorithmException e) {
            LOG.error("invalid_public_key_algorithm", e);
            throw new RuntimeException("Unable to create public key from PEM");
        } catch (InvalidKeySpecException e) {
            LOG.error("invalid_key_spec", e);
            throw new RuntimeException("Unable to create public key from PEM");
        }
    }
}
