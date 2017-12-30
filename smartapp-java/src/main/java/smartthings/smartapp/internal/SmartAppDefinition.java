package smartthings.smartapp.internal;

import com.google.inject.Injector;
import smartthings.smartapp.handlers.Handlers;
import smartthings.smartapp.handlers.HandlersBuilder;
import smartthings.smartapp.injectors.InjectorBuilder;
import smartthings.smartapp.specs.SmartAppSpec;
import smartthings.smartapp.handlers.SmartAppHandler;
import smartthings.smartapp.handlers.internal.DefaultHandlersBuilder;
import smartthings.smartapp.handlers.internal.DefaultSmartAppHandler;
import smartthings.smartapp.injectors.internal.DefaultInjectorBuilder;

import java.util.function.Consumer;

public final class SmartAppDefinition {

	private final SmartAppHandler smartAppHandler;
	private final Handlers handlers;
	private final Injector injector;

	private SmartAppDefinition(Handlers handlers, Injector injector) {
		this.smartAppHandler = new DefaultSmartAppHandler(handlers);
		this.handlers = handlers;
		this.injector = injector;
	}

	public SmartAppHandler getSmartAppHandler() {
		return smartAppHandler;
	}

	public Handlers getHandlers() {
		return handlers;
	}

	public Injector getInjector() {
		return injector;
	}

	public static SmartAppDefinition build(Consumer<SmartAppSpec> consumer) {
		SpecImpl spec = new SpecImpl();
		consumer.accept(spec);
		return spec.build();
	}

	private static class SpecImpl implements SmartAppSpec {
		private Injector injector;
		private HandlersBuilder handlersBuilder;

		@Override
		public SmartAppSpec injector(Injector injector) {
			this.injector = injector;
			return this;
		}

		@Override
		public SmartAppSpec injector(Consumer<? super InjectorBuilder> consumer) {
			InjectorBuilder builder = new DefaultInjectorBuilder();
			consumer.accept(builder);
			return injector(builder.build());
		}

		@Override
		public SmartAppSpec handlers(Consumer<? super HandlersBuilder> consumer) {
			HandlersBuilder builder = new DefaultHandlersBuilder();
			consumer.accept(builder);
			this.handlersBuilder = builder;
			return this;
		}

        private SmartAppDefinition build() {
			return new SmartAppDefinition(handlersBuilder.build(injector), injector);
		}
	}
}
