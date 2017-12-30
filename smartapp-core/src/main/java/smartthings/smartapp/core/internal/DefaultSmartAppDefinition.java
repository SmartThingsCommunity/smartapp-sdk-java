package smartthings.smartapp.core.internal;

import smartthings.smartapp.core.*;
import smartthings.smartapp.core.extensions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import v1.smartapps.*;

public class DefaultSmartAppDefinition implements SmartAppDefinition {

    private final InstallHandler installHandler;
    private final UpdateHandler updateHandler;
    private final UninstallHandler uninstallHandler;
    private final EventHandler eventHandler;
    private final PingHandler pingHandler;
    private final ConfigurationHandler configurationHandler;
    private final OAuthCallbackHandler oAuthCallbackHandler;
    private final List<PredicateHandler> predicateHandlers;

    private DefaultSmartAppDefinition(
        InstallHandler installHandler,
        UpdateHandler updateHandler,
        UninstallHandler uninstallHandler,
        EventHandler eventHandler,
        PingHandler pingHandler,
        ConfigurationHandler configurationHandler,
        OAuthCallbackHandler oAuthCallbackHandler,
        List<PredicateHandler> predicateHandlers
    ) {
        this.installHandler = installHandler;
        this.updateHandler = updateHandler;
        this.uninstallHandler = uninstallHandler;
        this.eventHandler = eventHandler;
        this.pingHandler = pingHandler;
        this.configurationHandler = configurationHandler;
        this.oAuthCallbackHandler = oAuthCallbackHandler;
        this.predicateHandlers = predicateHandlers;
    }

    public static SmartAppDefinition build(Consumer<SmartAppDefinitionSpec> consumer) {
        SpecImpl spec = new SpecImpl();
        consumer.accept(spec);
        return spec.build();
    }

    @Override
    public InstallHandler getInstallHandler() {
        return installHandler;
    }

    @Override
    public UpdateHandler getUpdateHandler() {
        return updateHandler;
    }

    @Override
    public UninstallHandler getUninstallHandler() {
        return uninstallHandler;
    }

    @Override
    public EventHandler getEventHandler() {
        return eventHandler;
    }

    @Override
    public PingHandler getPingHandler() {
        return pingHandler;
    }

    @Override
    public ConfigurationHandler getConfigurationHandler() {
        return configurationHandler;
    }

    @Override
    public OAuthCallbackHandler getOauthCallbackHandler() {
        return oAuthCallbackHandler;
    }

    @Override
    public List<PredicateHandler> getPredicateHandlers() {
        return predicateHandlers;
    }

    private static class SpecImpl implements SmartAppDefinitionSpec {

        private Supplier<InstallHandler> installHandler;
        private Supplier<UpdateHandler> updateHandler;
        private Supplier<UninstallHandler> uninstallHandler;
        private Supplier<EventHandler> eventHandler;
        private Supplier<PingHandler> pingHandler;
        private Supplier<ConfigurationHandler> configurationHandler;
        private Supplier<OAuthCallbackHandler> oAuthCallbackHandler;
        private List<PredicateHandler> predicateHandlers = new ArrayList<>();

        @Override
        public SmartAppDefinitionSpec install(InstallHandler handler) {
            installHandler = () -> handler;
            return this;
        }

        @Override
        public SmartAppDefinitionSpec update(UpdateHandler handler) {
            updateHandler = () -> handler;
            return this;
        }

        @Override
        public SmartAppDefinitionSpec uninstall(UninstallHandler handler) {
            uninstallHandler = () -> handler;
            return this;
        }

        @Override
        public SmartAppDefinitionSpec event(EventHandler handler) {
            eventHandler = () -> handler;
            return this;
        }

        @Override
        public SmartAppDefinitionSpec ping(PingHandler handler) {
            pingHandler = () -> handler;
            return this;
        }

        @Override
        public SmartAppDefinitionSpec configuration(ConfigurationHandler handler) {
            configurationHandler = () -> handler;
            return this;
        }

        @Override
        public SmartAppDefinitionSpec oauthCallback(OAuthCallbackHandler handler) {
            oAuthCallbackHandler = () -> handler;
            return this;
        }

        @Override
        public SmartAppDefinitionSpec when(Predicate<ExecutionRequest> predicate, Handler handler) {
            predicateHandlers.add(PredicateHandler.of(predicate, handler));
            return this;
        }

        private SmartAppDefinition build() {
            return new DefaultSmartAppDefinition(
                installHandler != null ? installHandler.get() : null,
                updateHandler != null ? updateHandler.get() : null,
                uninstallHandler != null ? uninstallHandler.get() : null,
                eventHandler != null ? eventHandler.get() : null,
                pingHandler != null ? pingHandler.get() : null,
                configurationHandler != null ? configurationHandler.get() : null,
                oAuthCallbackHandler != null ? oAuthCallbackHandler.get() : null,
                predicateHandlers
            );
        }
    }
}
