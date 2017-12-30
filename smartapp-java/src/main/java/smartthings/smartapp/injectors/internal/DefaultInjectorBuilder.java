package smartthings.smartapp.injectors.internal;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import smartthings.smartapp.injectors.InjectorBuilder;

import java.util.ArrayList;
import java.util.List;

public class DefaultInjectorBuilder implements InjectorBuilder {
    private List<Module> defaultModules = new ArrayList<>();
    private List<Module> modules = new ArrayList<>();

    @Override
    public InjectorBuilder install(Module module) {
        modules.add(module);
        return this;
    }

    @Override
    public Injector build() {
        return Guice.createInjector(Modules.override(defaultModules).with(modules));
    }
}
