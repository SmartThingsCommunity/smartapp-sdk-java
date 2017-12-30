package smartthings.smartapp.core.internal.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartthings.smartapp.core.Response;
import smartthings.smartapp.core.extensions.OAuthCallbackHandler;
import v1.smartapps.*;

public class DefaultOAuthCallbackHandler implements OAuthCallbackHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultOAuthCallbackHandler.class);

    @Override
    public ExecutionResponse handle(ExecutionRequest request) throws Exception {
        LOG.error("OAuthCallback lifecycle is not supported.");
        return Response.notFound();
    }
}
