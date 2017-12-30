package smartthings.smartapp.injectors;

import com.google.inject.Injector;
import com.google.inject.Module;

public interface InjectorBuilder {

    InjectorBuilder install(Module module);
    Injector build();
}
