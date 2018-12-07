package smartthings.sdk.smartapp.core;

import smartthings.sdk.smartapp.core.extensions.*;

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
