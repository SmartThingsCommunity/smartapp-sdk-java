package smartthings.smartapp.internal;

import smartthings.smartapp.SmartApp;
import smartthings.smartapp.specs.SmartAppSpec;
import smartthings.smartapps.ExecutionRequest;
import smartthings.smartapps.ExecutionResponse;
import java.util.function.Consumer;

public class DefaultSmartApp implements SmartApp {

	private final SmartAppDefinition definition;

	public DefaultSmartApp(Consumer<SmartAppSpec> smartappFactory) {
		this.definition = SmartAppDefinition.build(smartappFactory);
	}

	@Override
	public ExecutionResponse execute(ExecutionRequest request) throws Exception {
		switch (request.getLifecycle()) {
			case INSTALL:
			case UNINSTALL:
			case UPDATE:
			case CONFIGURATION:
			case PING:
			case EXECUTE:
			case OAUTH_CALLBACK:
			case EVENT:
				return ExecutionResponse.newExecutionResponseBuilder()
					.withStatusCode(200)
					.build();
			default:
				return ExecutionResponse.newExecutionResponseBuilder()
					.withStatusCode(404)
					.build();
		}
	}
}
