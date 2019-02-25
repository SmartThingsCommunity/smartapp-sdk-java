package com.smartthings.sdk.smartapp.core.extensions

import spock.lang.Specification


class HttpVerificationServiceSpec extends Specification {
    void "verify returns false when file does not exist"() {
        when:
        HttpVerificationService tester = new HttpVerificationService("resource that does not exist")
        boolean result = tester.verify(null)

        then:
        result == false
    }

    void "fails verification with exception with bad input file"() {
        given:
        Map<String, String> headers = new HashMap<>();

        when:
        HttpVerificationService tester = new HttpVerificationService("/bad_rsa.pub")
        tester.verify("POST", "uri", headers)

        then:
        thrown RuntimeException
    }
}
