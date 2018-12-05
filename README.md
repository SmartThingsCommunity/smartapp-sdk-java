# smartthings-sdk ![SmartThings](/docs/smartthings-logo.png) ![Java](/docs/java-logo.png)

A set of Java libraries for building SmartApps.
## Modules
### [smartapp-core](/smartapp-core) ([Documentation](/smartapp-core/README.md)) [![pipeline status](/../badges/master/pipeline.svg?job=master_build_core)](/../pipelines) [![coverage report](/../badges/master/coverage.svg?job=master_build_core)](/../commits/master)
Core SmartApp framework. Provides abilities for defining a SmartApp that could be used in many environments - AWS Lambda / Dropwizard / Ratpack / etc

### [smartapp-guice](/smartapp-guice) (Documentation) [![pipeline status](/../badges/master/pipeline.svg?job=master_build_guice)](/../pipelines) [![coverage report](/../badges/master/coverage.svg?job=master_build_guice)](/../commits/master)
An extension library that provides support for building a SmartApp with Guice dependency injection.

### smartapp-dropwizard-auth (coming soon)
An extension library that provides mechanisms for verifying SmartApp http signatures in a Dropwizard project.

### smartapp-lazybones (coming soon)
A templating extension for Lazybones that bootstraps out a basic Dropwizard SmartApp project.

### smartthings-client (coming soon)
An API library that provides useful utilities for working with the Subscription / Schedules / Device APIs

## SmartApp Example

<details>
<summary>Groovy (click to toggle)</summary>

```groovy
    SmartApp smartApp = SmartApp.of { spec ->
        spec
            .install({ req -> 
                // create subscriptions
                Response.ok() 
            })
            .update({ req -> 
                // delete subscriptions
                // create subscriptions
                Response.ok() 
            })                
            .configuration({ req ->
                ConfigurationResponseData data = ...// build config
                Response.ok(data) 
            })
            .event(EventHandler.of { eventSpec ->
                eventSpec
                    .onSubscription("switch", { event ->
                       // do something
                    })                       
                    .onSchedule("nightly", { event ->
                       // do something
                    })
                    .onEvent(
                        { event ->
                            // test event
                            true                                  
                        },
                        { event ->
                            // do something
                        }
                    )    
            })
    }
```

</details>

<details>
<summary>Java (click to toggle)</summary>

```java
    private final SmartApp smartApp = SmartApp.of(spec ->
        spec
            .install(request -> {
                return Response.ok();
            })
            .update(request -> {
                return Response.ok(UpdateResponseData.newInstance());
            })
            .configuration(request -> {
                return Response.ok(ConfigurationReponseData.newInstance());
            })
            .event(request -> {
                EventData eventData = request.getEventData();
                EventHandler.of(eventSpec ->
                        eventSpec
                                .onEvent(event -> {
                                    // when this predicate is true...
                                    return true;
                                }, event -> {
                                    // ...do something with event
                                })
                                .onSchedule("nightly", event -> {
                                    // do something
                                })
                                .onSubscription("switch", event -> {
                                    // do something
                                })
                );
                return Response.ok(EventResponseData.newInstance());
            })
        );
```

</details>

# To Run / Build
```
./gradlew clean build
```
