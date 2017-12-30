package smartthings.smartapp.handlers.internal;

import smartthings.smartapp.handlers.*;
import com.google.inject.Injector;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultHandlersBuilder implements HandlersBuilder {
    private Function<Injector, InstallHandler> installHandlerFactory;
    private Function<Injector, UninstallHandler> uninstallHandlerFactory;
    private Function<Injector, UpdateHandler> updateHandlerFactory;
    private Function<Injector, ConfigureHandler> configureHandlerFactory;
    private Function<Injector, OAuthCallbackHandler> oauthHandlerFactory;
    private Function<Injector, PingHandler> pingHandlerFactory;
    private Function<Injector, ExecuteHandler> executeHandlerFactory;
    private List<Function<Injector, PredicateEventHandler>> eventHandlersFactory = new ArrayList<>();

    @Override
    public HandlersBuilder install(Class<InstallHandler> clazz) {
        this.installHandlerFactory = (Injector injector) -> injector.getInstance(clazz);
        return this;
    }

    @Override
    public HandlersBuilder uninstall(Class<UninstallHandler> clazz) {
        this.uninstallHandlerFactory = (Injector injector) -> injector.getInstance(clazz);
        return this;
    }

    @Override
    public HandlersBuilder update(Class<UpdateHandler> clazz) {
        this.updateHandlerFactory = (Injector injector) -> injector.getInstance(clazz);
        return this;
    }

    @Override
    public HandlersBuilder configure(Class<ConfigureHandler> clazz) {
        this.configureHandlerFactory = (Injector injector) -> injector.getInstance(clazz);
        return this;
    }

    @Override
    public HandlersBuilder oauth(Class<OAuthCallbackHandler> clazz) {
        this.oauthHandlerFactory = (Injector injector) -> injector.getInstance(clazz);
        return this;
    }

    @Override
    public HandlersBuilder ping(Class<PingHandler> clazz) {
        this.pingHandlerFactory = (Injector injector) -> injector.getInstance(clazz);
        return this;
    }

    @Override
    public HandlersBuilder execute(Class<ExecuteHandler> clazz) {
        this.executeHandlerFactory = (Injector injector) -> injector.getInstance(clazz);
        return this;
    }

    @Override
    public HandlersBuilder event(Class<PredicateEventHandler> clazz) {
        this.eventHandlersFactory.add((Injector injector) -> injector.getInstance(clazz));
        return this;
    }

    @Override
    public Handlers build(Injector injector) {
        return new DefaultHandlers(
            installHandlerFactory.apply(injector),
            uninstallHandlerFactory.apply(injector),
            updateHandlerFactory.apply(injector),
            configureHandlerFactory.apply(injector),
            oauthHandlerFactory.apply(injector),
            pingHandlerFactory.apply(injector),
            executeHandlerFactory.apply(injector),
            eventHandlersFactory.stream()
                .map(f -> f.apply(injector))
                .collect(Collectors.toList())
        );
    }
}
