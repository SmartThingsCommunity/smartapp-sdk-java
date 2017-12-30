package smartthings.smartapp.core;

import v1.smartapps.*;

@FunctionalInterface
public interface Handler {
    ExecutionResponse handle(ExecutionRequest request) throws Exception;
}

