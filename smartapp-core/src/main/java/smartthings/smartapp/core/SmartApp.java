package smartthings.smartapp.core;

import smartthings.smartapp.core.internal.DefaultSmartApp;
import v1.smartapps.*;

import java.util.function.Consumer;

public interface SmartApp {

    ExecutionResponse execute(ExecutionRequest request);

    static SmartApp of(Consumer<SmartAppDefinitionSpec> spec) {
        return new DefaultSmartApp(spec);
    }

    static SmartApp of(SmartAppDefinition definition) {
        return new DefaultSmartApp(definition);
    }
}
