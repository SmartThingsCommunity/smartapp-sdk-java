package app.handlers

import smartthings.sdk.smartapp.core.Response
import smartthings.sdk.smartapp.core.models.AppLifecycle
import smartthings.sdk.smartapp.core.models.EventResponseData
import smartthings.sdk.smartapp.core.models.ExecutionRequest
import smartthings.sdk.smartapp.core.models.ExecutionResponse
import spock.lang.Specification


class AppEventHandlerSpec extends Specification {
    private final AppEventHandler eventHandler = new AppEventHandler()

    void "respond with simple 200"() {
        given:
        ExecutionRequest executionRequest = new ExecutionRequest().lifecycle(AppLifecycle.EVENT)
        ExecutionResponse expectedResponse = Response.ok(new EventResponseData())

        when:
        ExecutionResponse executionResponse = eventHandler.handle(executionRequest)

        then:
        executionResponse == expectedResponse
    }
}
