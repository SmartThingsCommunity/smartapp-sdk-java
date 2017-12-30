package smartthings.smartapp

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.google.inject.AbstractModule
import com.google.inject.Singleton
import smartthings.smartapp.handlers.ConfigureHandler
import smartthings.smartapp.handlers.ExecuteHandler
import smartthings.smartapp.handlers.InstallHandler
import smartthings.smartapp.handlers.OAuthCallbackHandler
import smartthings.smartapp.handlers.PingHandler
import smartthings.smartapp.handlers.PredicateEventHandler
import smartthings.smartapp.handlers.UninstallHandler
import smartthings.smartapp.handlers.UpdateHandler
import smartthings.smartapps.AppLifecycle
import smartthings.smartapps.ConfigurationData
import smartthings.smartapps.ConfigurationResponseData
import smartthings.smartapps.EventData
import smartthings.smartapps.EventResponseData
import smartthings.smartapps.ExecuteData
import smartthings.smartapps.ExecutionRequest
import smartthings.smartapps.ExecutionResponse
import smartthings.smartapps.InstallData
import smartthings.smartapps.InstallResponseData
import smartthings.smartapps.OAuthCallbackData
import smartthings.smartapps.OAuthCallbackResponseData
import smartthings.smartapps.PingData
import smartthings.smartapps.PingResponseData
import smartthings.smartapps.UninstallData
import smartthings.smartapps.UninstallResponseData
import smartthings.smartapps.UpdateData
import smartthings.smartapps.UpdateResponseData
import spock.lang.Specification

class SmartAppSpec extends Specification {

    void 'it should handle a lambda smartapp'() {
        given:
        TestRequestHandler handler = new TestRequestHandler()
        ExecutionRequest.Builder request = ExecutionRequest.newExecutionRequestBuilder()
            .withLifecycle(AppLifecycle.EXECUTE)
            .withExecutionId(UUID.randomUUID().toString())
        Context ctx = Mock(Context)

        when:
        ExecutionResponse response = handler.handleRequest(request, ctx)

        then:
        assert response
    }

}

class TestRequestHandler implements RequestHandler<ExecutionRequest.Builder, ExecutionResponse> {
    @Override
    ExecutionResponse handleRequest(ExecutionRequest.Builder request, Context context) {
        return SmartApp.of { spec ->
            spec
                .injector { injector ->
                    injector.install(new TestModule())
                }
                .handlers { handlers ->
                    handlers
                        .install(TestInstallHandler)
                        .uninstall(TestUninstallHandler)
                        .update(TestUpdateHandler)
                        .configure(TestConfigureHandler)
                        .ping(TestPingHandler)
                        .oauth(TestOAuthCallbackHandler)
                        .execute(TestExecuteHandler)
                        .event(TestEventHandler)
                }
        }.execute(request.build())
    }
}

@Singleton
class TestInstallHandler implements InstallHandler {
    @Override
    InstallResponseData handle(InstallData installData) throws Exception {
        return null
    }
}

@Singleton
class TestUpdateHandler implements UpdateHandler {
    @Override
    UpdateResponseData handle(UpdateData updateData) throws Exception {
        return null
    }
}

@Singleton
class TestConfigureHandler implements ConfigureHandler {
    @Override
    ConfigurationResponseData handle(ConfigurationData configurationData) throws Exception {
        return null
    }
}

@Singleton
class TestUninstallHandler implements UninstallHandler {
    @Override
    UninstallResponseData handle(UninstallData uninstallData) throws Exception {
        return null
    }
}

@Singleton
class TestExecuteHandler implements ExecuteHandler {
    @Override
    Object handle(ExecuteData executeData) throws Exception {
        return null
    }
}

@Singleton
class TestOAuthCallbackHandler implements OAuthCallbackHandler {
    @Override
    OAuthCallbackResponseData handle(OAuthCallbackData oAuthCallbackData) throws Exception {
        return null
    }
}

@Singleton
class TestPingHandler implements PingHandler {
    @Override
    PingResponseData handle(PingData pingData) throws Exception {
        return null
    }
}

@Singleton
class TestEventHandler implements PredicateEventHandler {
    @Override
    boolean canHandle(EventData data) {
        return true
    }

    @Override
    EventResponseData handle(EventData eventData) throws Exception {
        return null
    }
}

class TestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TestInstallHandler)
        bind(TestUpdateHandler)
        bind(TestConfigureHandler)
        bind(TestUninstallHandler)
        bind(TestExecuteHandler)
        bind(TestOAuthCallbackHandler)
        bind(TestPingHandler)
        bind(TestEventHandler)
    }
}