package com.smartthings.sdk.smartapp.core;

import com.smartthings.sdk.smartapp.core.extensions.*;
import com.smartthings.sdk.smartapp.core.models.*;

import java.util.function.Predicate;

public interface SmartAppDefinitionSpec {
    SmartAppDefinitionSpec install(InstallHandler handler);
    SmartAppDefinitionSpec update(UpdateHandler handler);
    SmartAppDefinitionSpec uninstall(UninstallHandler handler);
    SmartAppDefinitionSpec event(EventHandler handler);
    SmartAppDefinitionSpec ping(PingHandler handler);
    SmartAppDefinitionSpec configuration(ConfigurationHandler handler);
    SmartAppDefinitionSpec oauthCallback(OAuthCallbackHandler handler);
    SmartAppDefinitionSpec when(Predicate<ExecutionRequest> predicate, Handler handler);
    SmartAppDefinitionSpec requestPreprocessor(RequestPreprocessor requestPreprocessor);
}
