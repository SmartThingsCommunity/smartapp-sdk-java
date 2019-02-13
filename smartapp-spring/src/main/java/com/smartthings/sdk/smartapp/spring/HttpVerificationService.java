package com.smartthings.sdk.smartapp.spring;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.openssl.PEMParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

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

    public HttpVerificationService(Resource publicKeyResource) {
        if (!publicKeyResource.exists()) {
            // This is not an error until we are past the PING step.
            LOG.info("no public key yet; will only accept PING lifecycle requests");
            publicKey = null;
            return;
        }

        try {
            LOG.debug("Looking for public key file: " + publicKeyResource.getFilename());
            publicKey = new String(FileCopyUtils.copyToByteArray(publicKeyResource.getInputStream()), "UTF-8");
        } catch (IOException ioException) {
            LOG.error("Could not read public key file", ioException);
            throw new RuntimeException("Could not read public key file", ioException);
        }
    }

    public boolean verify(HttpServletRequest request) {
        if (publicKey == null) {
            LOG.error("Public key file not set up properly. Please see README (Configure Public Key) for"
                    + " directions and don't forget to restart this server.");
            return false;
        }
        boolean verified = verifyRequest(request);
        if (!verified) {
            LOG.error("Request not verified!");
        }
        return verified;
    }

    private boolean verifyRequest(HttpServletRequest request) {
        KeyPair pair = getRSAKeyPair();
        DefaultKeychain keychain = new DefaultKeychain();
        KeyId keyId = new UserFingerprintKeyId("SmartThings");
        keychain.add(new SSHKey(KeyFormat.SSH_RSA, pair));
        Verifier verifier = new DefaultVerifier(keychain, keyId);

        Authorization authorization = Authorization.parse(request.getHeader("Authorization"));

        if (authorization == null) {
            LOG.error("Request contains no authorization header");
            return false;
        }

        Challenge challenge = new Challenge("<preemptive>", authorization.getHeaders(),
                Collections.unmodifiableList(Arrays.asList(authorization.getAlgorithm())));

        Set<String> signedHeaders = authorization.getHeaders().stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        LOG.debug("contextPath: " + request.getContextPath());
        LOG.debug("pathInfo: " + request.getPathInfo());
        LOG.debug("requestURI: " + request.getRequestURI());

        RequestContent.Builder content = new RequestContent.Builder()
                .setRequestTarget(request.getMethod(), request.getRequestURI());

        List<String> headers = Collections.list(request.getHeaderNames());
        headers.stream()
        .filter(header -> signedHeaders.contains(header.toLowerCase()))
        .forEach(header -> content.addHeader(header, request.getHeader(header)));

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
