package smartthings.smartapp.handlers.internal;

import smartthings.smartapp.handlers.*;
import java.util.List;

public class DefaultHandlers implements Handlers {

    private final InstallHandler installHandler;
    private final UninstallHandler uninstallHandler;
    private final UpdateHandler updateHandler;
    private final ConfigureHandler configureHandler;
    private final OAuthCallbackHandler oauthHandler;
    private final PingHandler pingHandler;
    private final ExecuteHandler executeHandler;
    private final List<PredicateEventHandler> eventHandlers;

    public DefaultHandlers(InstallHandler installHandler, UninstallHandler uninstallHandler, UpdateHandler updateHandler, ConfigureHandler configureHandler, OAuthCallbackHandler oauthHandler, PingHandler pingHandler, ExecuteHandler executeHandler, List<PredicateEventHandler> eventHandlers) {
        this.installHandler = installHandler;
        this.uninstallHandler = uninstallHandler;
        this.updateHandler = updateHandler;
        this.configureHandler = configureHandler;
        this.oauthHandler = oauthHandler;
        this.pingHandler = pingHandler;
        this.executeHandler = executeHandler;
        this.eventHandlers = eventHandlers;
    }

    @Override
    public InstallHandler getInstallHandler() {
        return installHandler;
    }

    @Override
    public UninstallHandler getUninstallHandler() {
        return uninstallHandler;
    }

    @Override
    public UpdateHandler getUpdateHandler() {
        return updateHandler;
    }

    @Override
    public ConfigureHandler getConfigureHandler() {
        return configureHandler;
    }

    @Override
    public OAuthCallbackHandler getOauthHandler() {
        return oauthHandler;
    }

    @Override
    public PingHandler getPingHandler() {
        return pingHandler;
    }

    @Override
    public ExecuteHandler getExecuteHandler() {
        return executeHandler;
    }

    @Override
    public List<PredicateEventHandler> getEventHandlers() {
        return eventHandlers;
    }
}
