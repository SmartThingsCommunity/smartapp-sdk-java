package app;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Module;
import ratpack.config.internal.DefaultConfigDataBuilder;
import ratpack.http.Status;
import ratpack.server.RatpackServer;
import smartthings.sdk.smartapp.core.SmartApp;
import smartthings.sdk.smartapp.core.models.ExecutionRequest;
import smartthings.sdk.smartapp.guice.Guice;

import static ratpack.jackson.Jackson.json;


public class App {
    public static void main(String... args) throws Exception {
        Module appModule = new AppModule();
        SmartApp smartApp = SmartApp.of(Guice.smartapp(bindings -> bindings.module(appModule)));
        RatpackServer.start(server -> {
            ObjectMapper objectMapper = DefaultConfigDataBuilder.newDefaultObjectMapper()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            server
                .registry(ratpack.guice.Guice.registry(bindingsSpec -> {
                    bindingsSpec.module(appModule);
                    bindingsSpec.bindInstance(ObjectMapper.class, objectMapper);
                }))
                .handlers(chain -> chain
                    .get(ctx -> ctx.getResponse()
                        .status(Status.FORBIDDEN)
                        .send("This app only functions as a SmartThings Automation webhook endpoint app"))
                    .post("smartapp", ctx -> {
                        ctx.parse(ExecutionRequest.class).then(request -> {
                            ctx.render(json(smartApp.execute(request)));
                        });
                    })
                );
            }
        );
    }
}
