package smartthings.smartapp.core;

import smartthings.smartapp.core.extensions.*;

import java.util.List;

public interface SmartAppDefinition {
    InstallHandler getInstallHandler();
    UpdateHandler getUpdateHandler();
    UninstallHandler getUninstallHandler();
    EventHandler getEventHandler();
    PingHandler getPingHandler();
    ConfigurationHandler getConfigurationHandler();
    OAuthCallbackHandler getOauthCallbackHandler();
    List<PredicateHandler> getPredicateHandlers();
}
