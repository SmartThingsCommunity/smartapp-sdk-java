package smartthings.smartapp.core.extensions;

import smartthings.smartapp.core.EventSpec;
import smartthings.smartapp.core.Handler;
import smartthings.smartapp.core.internal.handlers.DefaultEventHandler;
import java.util.function.Consumer;

/**
 * Marker interface for delineating a handler for Event lifecycle.
 */
public interface EventHandler extends Handler {

    static EventHandler of (Consumer<? super EventSpec> consumer) {
        DefaultEventHandler.EventSpecImpl spec = new DefaultEventHandler.EventSpecImpl();
        consumer.accept(spec);
        return spec.build();
    }
}
