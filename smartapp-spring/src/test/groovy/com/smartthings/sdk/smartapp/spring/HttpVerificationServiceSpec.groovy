package com.smartthings.sdk.smartapp.spring

import javax.servlet.http.HttpServletRequest

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource

import spock.lang.Specification


class HttpVerificationServiceSpec extends Specification {
    void "initialization fails if file exists but is unreadable"() {
        given:
        Resource resource = Mock()
        resource.exists() >> true
        resource.getInputStream() >> { throw new IOException("cannot read file") }

        when:
        new HttpVerificationService(resource)

        then:
        thrown RuntimeException
    }

    void "verify returns false when file does not exist"() {
        given:
        Resource resource = Mock()
        resource.exists() >> false

        when:
        HttpVerificationService tester = new HttpVerificationService(resource)
        boolean result = tester.verify(null)

        then:
        result == false
    }

    void "fails verification with exception with bad input file"() {
        given:
        Resource resource = new ClassPathResource("bad_rsa.pub")
        HttpServletRequest request = Mock()

        request.getHeader("Authorization") >> "todo"
        request.getMethod() >> "POST"
        request.getRequestURI() >> "todo"
        request.getHeaderNames() >> ["Authorization"]
        request.getHeader("Authorization") >> "todo"
        request.getHeader("Authorization") >> "todo"

        when:
        HttpVerificationService tester = new HttpVerificationService(resource)
        tester.verify(request)

        then:
        thrown RuntimeException
    }
}
