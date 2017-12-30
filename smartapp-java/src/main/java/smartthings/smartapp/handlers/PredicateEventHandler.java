package smartthings.smartapp.handlers;

import smartthings.smartapps.EventData;

public interface PredicateEventHandler extends EventHandler {

    boolean canHandle(EventData data);
}
