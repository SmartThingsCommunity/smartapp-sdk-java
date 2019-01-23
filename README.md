# smartthings-sdk ![SmartThings](/docs/smartthings-logo.png) ![Java](/docs/java-logo.png)

A set of JVM libraries for building SmartApps.

## What is a SmartApp?
SmartApps are an example of a [SmartThings Automation](https://smartthings.developer.samsung.com/develop/getting-started/automation.html). Automations allow users to control the SmartThings ecosystem without manual intervention. Creating a SmartApp allows you to control and get status notifications from SmartThings devices using the [SmartThings REST API](https://smartthings.developer.samsung.com/develop/api-ref/st-api.html).

![automation smartapp](https://smartthings.developer.samsung.com/develop/getting-started/img/automation_smartapp.png)

To learn more about what a SmartApp is and how you can create interesting automations, please visit the [developer portal documentation](https://smartthings.developer.samsung.com/develop/guides/smartapps/basics.html).


## What's in this SDK?
### Modules
#### [smartapp-core](/smartapp-core) ([Documentation](smartapp-core/README.md)) [![pipeline status](/../badges/master/pipeline.svg?job=master_build_core)](/../pipelines) [![coverage report](/../badges/master/coverage.svg?job=master_build_core)](/../commits/master)
Core SmartApp framework. Provides abilities for defining a SmartApp that could be used in many environments - AWS Lambda / Dropwizard / Ratpack / etc

#### [smartthings-client](/smartthings-client) (Documentation) [![pipeline status](/../badges/master/pipeline.svg?job=master_build_client)](/../pipelines) [![coverage report](/../badges/master/coverage.svg?job=master_build_client)](/../commits/master)
An API library that provides useful utilities for working with the Subscription / Schedules / Device APIs

#### Extension Libraries

##### [smartapp-guice](/smartapp-guice) (Documentation) [![pipeline status](/../badges/master/pipeline.svg?job=master_build_guice)](/../pipelines) [![coverage report](/../badges/master/coverage.svg?job=master_build_guice)](/../commits/master)
An extension library that provides support for building a SmartApp with Guice dependency injection.

##### [smartapp-spring](/smartapp-spring) ([Documentation](smartapp-spring/README.md)) [![pipeline status](/../badges/master/pipeline.svg?job=master_build_spring)](/../pipelines) [![coverage report](/../badges/master/coverage.svg?job=master_build_spring)](/../commits/master)
An extension library that provides support for building a SmartApp with Spring dependency injection.

##### smartapp-dropwizard-auth (coming soon)
An extension library that provides mechanisms for verifying SmartApp http signatures in a Dropwizard project.

##### smartapp-lazybones (coming soon)
A templating extension for Lazybones that bootstraps out a basic Dropwizard SmartApp project.

### Examples

#### [kotlin-smartapp](examples/kotlin-smartapp) ([Documentation](examples/kotlin-smartapp/README.md)) ![kotlin-logo](docs/kotlin-logo.png) ![ktor-logo](docs/ktor-logo.png) [![pipeline status](/../badges/master/pipeline.svg?job=master_build_examples_kotlin)](/../pipelines) [![coverage report](/../badges/master/coverage.svg?job=master_build_examples_kotlin)](/../commits/master)
This Kotlin example implements the Java `smartapp-core` library with a simple [Ktor](https://ktor.io/) server.

#### [java-ratpack-guice-smartapp](examples/java-ratpack-guice-smartapp) ([Documentation](examples/java-ratpack-guice-smartapp/README.md)) ![java-logo](docs/java-logo.png) ![ratpack-logo](docs/ratpack-logo.png) [![pipeline status](/../badges/master/pipeline.svg?job=master_build_examples_java_ratpack_guice)](/../pipelines) [![coverage report](/../badges/master/coverage.svg?job=master_build_examples_java_ratpack_guice)](/../commits/master)
This Java example implements the Java `smartapp-core` library with a Ratpack server and uses Guice for dependency managment.

#### [java-spring-smartapp](examples/java-spring-smartapp) ([Documentation](examples/java-spring-smartapp/README.md)) ![java-logo](docs/java-logo.png) ![spring-logo](docs/spring-logo.png) [![pipeline status](/../badges/master/pipeline.svg?job=master_build_examples_java_spring)](/../pipelines) [![coverage report](/../badges/master/coverage.svg?job=master_build_examples_java_spring)](/../commits/master)
This Java example implements the Java `smartapp-core` library using Spring Boot.

#### Getting Started

Take a quick look at how SmartApps are declared in various languages.

<details>
<summary>Kotlin (click to toggle)</summary>

```kotlin
package app

val smartApp: SmartApp = SmartApp.of { spec ->
    spec
        .configuration(Configuration())
        .install {
            Response.ok(InstallResponseData())
        }
        .update {
            Response.ok(UpdateResponseData())
        }
        .event {
            Response.ok(EventResponseData())
        }
        .uninstall {
            Response.ok(UninstallResponseData())
        }
}

fun Application.main() {
    install(Routing) {
        post("/smartapp") {
            call.respond(smartApp.execute(call.receive()))
        }
    }
}

```

</details>

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

## To Run / Build
```
./gradlew clean build
```
