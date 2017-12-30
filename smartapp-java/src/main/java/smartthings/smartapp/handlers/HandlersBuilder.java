package smartthings.smartapp.handlers;

import com.google.inject.Injector;

public interface HandlersBuilder {

    HandlersBuilder install(Class<InstallHandler> clazz);
    HandlersBuilder uninstall(Class<UninstallHandler> clazz);
    HandlersBuilder update(Class<UpdateHandler> clazz);
    HandlersBuilder configure(Class<ConfigureHandler> clazz);
    HandlersBuilder oauth(Class<OAuthCallbackHandler> clazz);
    HandlersBuilder ping(Class<PingHandler> clazz);
    HandlersBuilder execute(Class<ExecuteHandler> clazz);
    HandlersBuilder event(Class<PredicateEventHandler> clazz);
    Handlers build(Injector injector);
    
}
