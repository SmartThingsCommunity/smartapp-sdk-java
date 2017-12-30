package smartthings.smartapp.core.internal.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartthings.smartapp.core.Response;
import smartthings.smartapp.core.extensions.PingHandler;
import v1.smartapps.*;

public class DefaultPingHandler implements PingHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultPingHandler.class);

    @Override
    public ExecutionResponse handle(ExecutionRequest request) throws Exception {
        if (AppLifecycle.PING != request.getLifecycle()) {
            LOG.error("Invalid lifecycle for PING handler.  lifecycle={}", request.getLifecycle());
            throw new IllegalArgumentException("Unsupported lifecycle for PingHandler");
        }

        return Response.ok(
            new PingResponseData()
                .challenge(request.getPingData().getChallenge())
        );
    }
}
