package smartthings.smartapp.core;

public class NotInRegistryException extends Exception {
    public NotInRegistryException() {
        super("Instance not found in registry.");
    }

    public NotInRegistryException(Throwable t) {
        super(t);
    }
}
