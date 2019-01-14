package app

import com.google.inject.Guice
import com.google.inject.Injector
import smartthings.sdk.smartapp.core.Response
import smartthings.sdk.smartapp.core.extensions.InstallHandler
import smartthings.sdk.smartapp.core.extensions.PingHandler
import smartthings.sdk.smartapp.core.extensions.UninstallHandler
import smartthings.sdk.smartapp.core.extensions.UpdateHandler
import smartthings.sdk.smartapp.core.models.AppLifecycle
import smartthings.sdk.smartapp.core.models.ExecutionRequest
import smartthings.sdk.smartapp.core.models.ExecutionResponse
import smartthings.sdk.smartapp.core.models.InstallResponseData
import smartthings.sdk.smartapp.core.models.PingData
import smartthings.sdk.smartapp.core.models.PingResponseData
import smartthings.sdk.smartapp.core.models.UninstallResponseData
import smartthings.sdk.smartapp.core.models.UpdateResponseData
import spock.lang.Specification


class AppModuleSpec extends Specification {
    private final Injector injector = Guice.createInjector(new AppModule())

    void "respond to ping with simple 200"() {
        given:
        ExecutionRequest executionRequest = new ExecutionRequest()
            .lifecycle(AppLifecycle.PING)
            .pingData(new PingData().challenge("challenge_text"))
        ExecutionResponse expectedResponse = Response.ok(new PingResponseData().challenge("challenge_text"))

        when:
        PingHandler pingHandler = injector.getInstance(PingHandler)
        ExecutionResponse executionResponse = pingHandler.handle(executionRequest)

        then:
        executionResponse == expectedResponse
    }

    void "respond to install with simple 200"() {
        given:
        ExecutionRequest executionRequest = new ExecutionRequest().lifecycle(AppLifecycle.INSTALL)
        ExecutionResponse expectedResponse = Response.ok(new InstallResponseData())

        when:
        InstallHandler installHandler = injector.getInstance(InstallHandler)
        ExecutionResponse executionResponse = installHandler.handle(executionRequest)

        then:
        executionResponse == expectedResponse
    }

    void "respond to update with simple 200"() {
        given:
        ExecutionRequest executionRequest = new ExecutionRequest().lifecycle(AppLifecycle.UPDATE)
        ExecutionResponse expectedResponse = Response.ok(new UpdateResponseData())

        when:
        UpdateHandler updateHandler = injector.getInstance(UpdateHandler)
        ExecutionResponse executionResponse = updateHandler.handle(executionRequest)

        then:
        executionResponse == expectedResponse
    }

    void "respond to uninstall with simple 200"() {
        given:
        ExecutionRequest executionRequest = new ExecutionRequest().lifecycle(AppLifecycle.UNINSTALL)
        ExecutionResponse expectedResponse = Response.ok(new UninstallResponseData())

        when:
        UninstallHandler uninstallHandler = injector.getInstance(UninstallHandler)
        ExecutionResponse executionResponse = uninstallHandler.handle(executionRequest)

        then:
        executionResponse == expectedResponse
    }
}
