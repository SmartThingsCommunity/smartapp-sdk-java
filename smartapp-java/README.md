# smartapp-core

A very, very rough framework for building Endpoint SmartApps.  The paradigms being implemented borrow
heavily from Ratpack concepts.

The following example taken from `SmartAppSpec.groovy` demonstrates how you might build an AWS Lambda powered SmartApp:

```
class TestRequestHandler implements RequestHandler<ExecutionRequest.Builder, ExecutionResponse> {
    @Override
    ExecutionResponse handleRequest(ExecutionRequest.Builder request, Context context) {
        return SmartApp.of { spec ->
            spec
                .injector { injector ->
                    injector.install(new TestModule())
                }
                .handlers { handlers ->
                    handlers
                        .install(TestInstallHandler)
                        .uninstall(TestUninstallHandler)
                        .update(TestUpdateHandler)
                        .configure(TestConfigureHandler)
                        .ping(TestPingHandler)
                        .oauth(TestOAuthCallbackHandler)
                        .execute(TestExecuteHandler)
                        .event(TestEventHandler)
                }
        }.execute(request, context)
    }
}
```

To me the cool thing about this approach is we can further enhance that DSL to be more powerful.
For example, it wouldn't be too difficult to write a Groovy wrapper DSL (Like Ratpack),
that essentially turns it into something akin to our legacy Groovy SmartApps.