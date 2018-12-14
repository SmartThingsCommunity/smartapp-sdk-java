package smartthings.sdk.smartapp.core;

import smartthings.sdk.smartapp.core.models.*;

@FunctionalInterface
public interface Handler {
    ExecutionResponse handle(ExecutionRequest request) throws Exception;
}

