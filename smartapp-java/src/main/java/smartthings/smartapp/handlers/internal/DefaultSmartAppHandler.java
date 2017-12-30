package smartthings.smartapp.handlers.internal;

import smartthings.smartapp.handlers.Handlers;
import smartthings.smartapp.handlers.SmartAppHandler;
import smartthings.smartapps.ExecutionRequest;
import smartthings.smartapps.ExecutionResponse;

public class DefaultSmartAppHandler implements SmartAppHandler {

    private final Handlers handlers;

    public DefaultSmartAppHandler(Handlers handlers) {
        this.handlers = handlers;
    }

    @Override
    public ExecutionResponse handle(ExecutionRequest executionRequest) throws Exception {
        return ExecutionResponse.newExecutionResponseBuilder()
            .withStatusCode(200)
            .build();
    }
}
