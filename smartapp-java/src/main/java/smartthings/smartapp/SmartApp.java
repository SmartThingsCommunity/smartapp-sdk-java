package smartthings.smartapp;

import smartthings.smartapp.internal.DefaultSmartApp;
import smartthings.smartapp.specs.SmartAppSpec;
import smartthings.smartapps.ExecutionRequest;
import smartthings.smartapps.ExecutionResponse;

import java.util.function.Consumer;

public interface SmartApp {

	ExecutionResponse execute(ExecutionRequest request) throws Exception;

	static SmartApp of(Consumer<SmartAppSpec> spec) {
		return new DefaultSmartApp(spec);
	}
}
