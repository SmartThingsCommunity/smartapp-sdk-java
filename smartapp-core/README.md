# smartapp-core

Create easily-readable SmartThings SmartApps with this DSL-like fluent Java library.

## Overview

This library aims to make developing your own JVM-based SmartApps easy, intuitive, and flexible. You can write and distribute your SmartApps in any way you'd like. 

## Developers

### Requirements

* Java 1.8+
* SmartThings developer account

### Installation

Coming soon

### Usage

#### Basics
> Note: Although you can easily use this library alongside any web framework of your choice, the example below uses Spring Boot. 

1. Create a definition of your SmartApp (_fig. A_) 
2. Listen for incoming HTTP requests and execute the SmartApp (_fig. B_)
3. Add your additional response logic.

For more information on the SmartApp API reference (request/response expectations) please [view the API reference documentation](https://smartthings.developer.samsung.com/develop/api-ref/smartapps-v1.html), or for additional insight, the [SmartApp guide](https://smartthings.developer.samsung.com/develop/guides/smartapps/basics.html).  

_Fig. A_ Declare the SmartApp specification 
```java
public class AppController {
    private final SmartApp smartApp = SmartApp.of(spec ->
        spec
            .install(request -> Response.ok())
            .update(request -> Response.ok(UpdateResponseData.newInstance()))
            .configuration(request -> Response.ok(ConfigurationResponseData.newInstance()))
            .event(request -> Response.ok(EventResponseData.newInstance()))
            .uninstall(request -> Response.ok(UninstallResponseData.newInstance()))
    );
    
}
```

_Fig. B_ Execute the SmartApp for an incoming response 
```java
public class AppController {
    /* SmartApp declaration removed for brevity */
    
    @RequestMapping("/")
    public ExecutionResponse index(@RequestBody ExecutionRequest executionRequest) {
         return smartApp.execute(executionRequest);
    }
}
```

#### Required handlers

By default, some lifecycle events are handled for you. If you wish, you may override them at any time by creating the definition.

| Lifecycle Handler             | Required | Has Default Implementation |
|:------------------------------|:--------:|:--------------------------:|
| `AppLifecycle.EVENT`          | ✅       | 🚫                         |
| `AppLifecycle.INSTALL`        | ✅       | 🚫                         |
| `AppLifecycle.UPDATE`         | ✅       | 🚫                         |
| `AppLifecycle.UNINSTALL`      | ✅       | ✅                         |
| `AppLifecycle.PING`           | 🚫       | ✅                         |
| `AppLifecycle.OAUTH_CALLBACK` | 🚫       | 🚫                         |


#### Handling unsupported lifecycle events

With `SmartAppDefinitionSpec.when(Predicate<ExecutionRequest>, Handler)`, you can handle "custom" event lifecycles even if this library does not explicitly support it yet. For example, if the event `EXECUTE` is available in the API, but not in `smartapp-core`, you may use the predicate handler to check if the request matches `"EXECUTE"` and respond accordingly.

#### Responses

##### `notFound():ExecutionResponse`

Signifies to the SmartThings platform that the app definition doesn't know about that specific lifecycle event. 

##### `ok():ExecutionResponse`
Respond to request with an empty response object.

##### `ok(ConfigurationResponseData):ExecutionResponse`

Respond to initialization and page requests.

##### `ok(EventResponseData):ExecutionResponse`

Respond to event lifecycle requests.

##### `ok(InstallResponseData):ExecutionResponse`

Respond to installation update requests.

##### `ok(PingResponseData):ExecutionResponse`

Respond to ping lifecycle requests which are sent by the platform to ensure your application is online.

##### `ok(UninstallResponseDataok):ExecutionResponse`

Respond to uninstallation requests.

##### `ok(UpdateResponseData):ExecutionResponse`

Respond to configuration update requests.

##### `status(int):ExecutionResponse`

Response to request with an arbitrary HTTP status code.
