package smartthings.smartapp.guice

import com.google.inject.AbstractModule
import com.google.inject.Provides
import smartthings.sdk.smartapp.core.Response
import smartthings.sdk.smartapp.core.SmartApp
import smartthings.sdk.smartapp.core.extensions.ConfigurationHandler
import smartthings.sdk.smartapp.core.extensions.EventHandler
import smartthings.sdk.smartapp.core.extensions.InstallHandler
import smartthings.sdk.smartapp.core.extensions.OAuthCallbackHandler
import smartthings.sdk.smartapp.core.extensions.PingHandler
import smartthings.sdk.smartapp.core.extensions.UninstallHandler
import smartthings.sdk.smartapp.core.extensions.UpdateHandler
import v1.smartapps.*
import spock.lang.Specification

class SmartAppSpec extends Specification {
    void 'it should execute a smartapp x'() {
        given:
        ExecutionRequest request = new ExecutionRequest()
            .lifecycle(AppLifecycle.INSTALL)
            .executionId(UUID.randomUUID().toString())

        SmartApp smartApp = SmartApp.of (
            Guice.smartapp { bindings ->
                bindings.module(new TestAppModule())
            }
        )

        when:
        ExecutionResponse response = smartApp.execute(request)

        then:
        assert response.statusCode == 200
    }
}

class TestAppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MyInstallHandler)
    }

    @Provides
    UninstallHandler uninstallHandler() {
        { req -> Response.ok() }
    }

    @Provides
    UpdateHandler updateHandler() {
        { req -> Response.ok() }
    }

    @Provides
    ConfigurationHandler configurationHandler() {
        { req -> Response.ok() }
    }

    @Provides
    PingHandler pingHandler() {
        { req -> Response.ok() }
    }

    @Provides
    OAuthCallbackHandler oAuthCallbackHandler() {
        { req -> Response.ok() }
    }

    @Provides
    EventHandler eventHandler() {
        { req -> Response.ok() }
    }
}

class MyInstallHandler implements InstallHandler {
    @Override
    ExecutionResponse handle(ExecutionRequest request) throws Exception {
        return Response.ok()
    }
}
