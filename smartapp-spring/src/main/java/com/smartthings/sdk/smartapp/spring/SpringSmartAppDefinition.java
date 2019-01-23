package com.smartthings.sdk.smartapp.spring;

import com.smartthings.sdk.smartapp.core.Handler;
import com.smartthings.sdk.smartapp.core.PredicateHandler;
import com.smartthings.sdk.smartapp.core.SmartAppDefinition;
import com.smartthings.sdk.smartapp.core.extensions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;


public class SpringSmartAppDefinition implements SmartAppDefinition {
    private static final Logger LOG = LoggerFactory.getLogger(SpringSmartAppDefinition.class);

    private final PingHandler pingHandler;
    private final ConfigurationHandler configurationHandler;
    private final InstallHandler installHandler;
    private final UpdateHandler updateHandler;
    private final OAuthCallbackHandler oAuthCallbackHandler;
    private final EventHandler eventHandler;
    private final UninstallHandler uninstallHandler;
    private final List<PredicateHandler> predicateHandlers;

    public SpringSmartAppDefinition(PingHandler pingHandler, ConfigurationHandler configurationHandler,
                                    InstallHandler installHandler, UpdateHandler updateHandler,
                                    OAuthCallbackHandler oAuthCallbackHandler, EventHandler eventHandler,
                                    UninstallHandler uninstallHandler, List<PredicateHandler> predicateHandlers) {
        this.pingHandler = pingHandler;
        this.configurationHandler = configurationHandler;
        this.installHandler = installHandler;
        this.updateHandler = updateHandler;
        this.oAuthCallbackHandler = oAuthCallbackHandler;
        this.eventHandler = eventHandler;
        this.uninstallHandler = uninstallHandler;
        this.predicateHandlers = predicateHandlers;
    }

    public static SpringSmartAppDefinition of(ApplicationContext applicationContext) {
        PingHandler pingHandler = findHandler(applicationContext, PingHandler.class, false);
        ConfigurationHandler configurationHandler = findHandler(applicationContext, ConfigurationHandler.class, true);
        InstallHandler installHandler = findHandler(applicationContext, InstallHandler.class, true);
        UpdateHandler updateHandler = findHandler(applicationContext, UpdateHandler.class, true);
        OAuthCallbackHandler oAuthCallbackHandler = findHandler(applicationContext, OAuthCallbackHandler.class, false);
        EventHandler eventHandler = findHandler(applicationContext, EventHandler.class, true);
        UninstallHandler uninstallHandler = findHandler(applicationContext, UninstallHandler.class, false);
        List<PredicateHandler> predicateHandlers =
            new ArrayList<>(applicationContext.getBeansOfType(PredicateHandler.class).values());
        return new SpringSmartAppDefinition(pingHandler, configurationHandler, installHandler, updateHandler,
            oAuthCallbackHandler, eventHandler, uninstallHandler, predicateHandlers);
    }

    private static <T extends Handler> T findHandler(ApplicationContext applicationContext, Class<T> klass,
                                                     boolean required) {
        try {
            return applicationContext.getBean(klass);
        } catch (BeansException beansException) {
            if (required) {
                LOG.error("could not find required " + klass.getSimpleName() + " in Spring ApplicationContext",
                    beansException);
                throw beansException;
            } else {
                LOG.debug("did not find optional {} in Spring ApplicationContext", klass.getSimpleName());
                return null;
            }
        }
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
    public InstallHandler getInstallHandler() {
        return installHandler;
    }

    @Override
    public UpdateHandler getUpdateHandler() {
        return updateHandler;
    }

    @Override
    public OAuthCallbackHandler getOauthCallbackHandler() {
        return oAuthCallbackHandler;
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
    public List<PredicateHandler> getPredicateHandlers() {
        return predicateHandlers;
    }
}
