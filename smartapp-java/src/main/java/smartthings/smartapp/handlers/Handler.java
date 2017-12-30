package smartthings.smartapp.handlers;

@FunctionalInterface
public interface Handler<I,O> {
	O handle(I i) throws Exception;
}
