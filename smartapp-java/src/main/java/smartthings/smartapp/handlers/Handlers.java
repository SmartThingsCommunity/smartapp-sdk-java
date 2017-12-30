package smartthings.smartapp.handlers;

import java.util.List;

public interface Handlers {
    InstallHandler getInstallHandler();
    UninstallHandler getUninstallHandler();
    UpdateHandler getUpdateHandler();
    ConfigureHandler getConfigureHandler();
    OAuthCallbackHandler getOauthHandler();
    PingHandler getPingHandler();
    ExecuteHandler getExecuteHandler();
    List<PredicateEventHandler> getEventHandlers();
}
