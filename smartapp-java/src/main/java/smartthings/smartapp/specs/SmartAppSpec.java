package smartthings.smartapp.specs;

import com.google.inject.Injector;
import smartthings.smartapp.handlers.HandlersBuilder;
import smartthings.smartapp.injectors.InjectorBuilder;

import java.util.function.Consumer;

public interface SmartAppSpec {
	SmartAppSpec injector(Injector injector);
	SmartAppSpec injector(Consumer<? super InjectorBuilder> consumer);
	SmartAppSpec handlers(Consumer<? super HandlersBuilder> consumer);
}
