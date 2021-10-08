# SmartThings SmartApp SDK Java (Preview) ![SmartThings](docs/smartthings-logo.png) ![Java](docs/java-logo.png)

<p align="center">
<a href="https://search.maven.org/search?q=g:%22com.smartthings.sdk%22"><img src="https://img.shields.io/maven-central/v/com.smartthings.sdk/smartapp-core.svg?label=Maven%20Central" /></a>
<a href="https://circleci.com/gh/SmartThingsCommunity/smartapp-sdk-java/tree/master"><img src="https://circleci.com/gh/SmartThingsCommunity/smartapp-sdk-java/tree/master.svg?style=svg&circle-token=21da1691c64a0b734e55ada5d591b477347a2936" /></a>
<a href="https://codecov.io/gh/SmartThingsCommunity/smartapp-sdk-java"><img src="https://codecov.io/gh/SmartThingsCommunity/smartapp-sdk-java/branch/master/graph/badge.svg?token=Zy5sgPRzLd"></a>
<a href="https://snyk.io/test/github/SmartThingsCommunity/smartapp-sdk-java"><img src="https://snyk.io/test/github/SmartThingsCommunity/smartapp-sdk-java/badge.svg" alt="Known Vulnerabilities" data-canonical-src="https://snyk.io/test/github/SmartThingsCommunity/smartapp-sdk-java" style="max-width:100%;"></a>
    <a href="https://smartthingsdev.slack.com/messages/CG8D8PS6B"><img src="https://badgen.net/badge//smartthingsdev?icon=slack" /></a>
</p>

> **Welcome!**  This SDK is currently in _**preview**_ and is not recommended for use in production applications at this time. We welcome your bug reports, feature requests, and contributions.

## Getting Started

This SDK includes a set of JVM libraries for building Webhook and AWS Lambda SmartApps, and interacting with the public SmartThings API.

### Prerequisites

* Java 1.8+
* A Samsung account

### What is a SmartApp?

SmartApps are custom applications that execute outside of the SmartThings Platform. All of the SmartApp execution will happen on the server or Lambda that you control. The complexity of execution and the number of expected users will need to be examined to understand the hardware or execution requirements your app needs to handle. Your application will respond to lifecycle events sent from SmartThings to perform actions on behalf of your users and will execute any scheduled tasks that you have created in the SmartApp. Creating a SmartApp allows you to control and get status notifications from SmartThings devices using the [SmartThings API](https://developer-preview.smartthings.com/api/public#section/Overview).

> Visit the [SmartThings developer documentation](https://developer-preview.smartthings.com/docs/connected-services/smartapp-basics) to earn more about SmartApps.

### Hosting Your SmartApp

There are two distinct options for hosting your SmartApp: AWS Lambda and Webhook.

**Webhook** SmartApps are any publicly-accessible web server that will receive a POST request payload.

**AWS Lambda** SmartApps are hosted in the Amazon Web Services cloud and are invoked by **ARN** instead of a public-DNS address.

The best hosting option for your Automation depends on a number of factors, both objective and subjective.

To learn more about SmartApps, including [choosing the best hosting option for your SmartApp](https://developer-preview.smartthings.com/docs/connected-services/hosting/choose-a-solution), visit the [SmartThings developer documentation](https://developer-preview.smartthings.comd).

### Declaring Your SmartApp

Take a quick look at how SmartApps are declared in various languages.

<details>
<summary>Kotlin</summary>

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
<summary>Groovy</summary>

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
<summary>Java</summary>

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

## Modules and Extension Libraries

### Modules

- The [smartapp-core](/smartapp-core) module provides the core SmartApp framework, enabling you to define a SmartApp that can be used in many different environments including AWS Lambda, Dropwizard, Ratpack, and more.

- The [smartthings-client](/smartthings-client) module is an API library that provides useful utilities for working with the Subscription, Schedules, and Device APIs.

### Extension Libraries

- The [smartapp-guice](/smartapp-guice) extension library provides support for building a SmartApp with Guice dependency injection.

- The [smartapp-spring](/smartapp-spring) extension library provides support for building a SmartApp with Spring dependency injection.

- The [smartapp-contextstore-dynamodb](/smartapp-contextstore-dynamodb) extension library implements a [context store](smartapp-core/README.md#context-store) using DynamoDB.

## Adding the SDK to your build

Several artifacts are published to the Maven central repository under the `com.smartthings.sdk` group.

* `smartapp-core` - Core SmartApp Framework
  * `smartapp-guice` - Extension library for use with Google Guice
  * `smartapp-spring` - Extension library for use with Spring Dependency Injection
  * `smartapp-contextstore-dynamodb` - Extension library to use DynamoDB to
    store installed application context data.
* `smartthings-client` - Library for working with SmartThings APIs

Import the library dependencies as needed:

<details>
    <summary>Apache Maven</summary>

```xml
<dependency>
  <groupId>com.smartthings.sdk</groupId>
  <artifactId>smartapp-core</artifactId>
  <version>0.0.4-PREVIEW</version>
  <type>pom</type>
</dependency>
```

</details>

<details>
    <summary>Gradle Groovy DSL</summary>

```groovy
implementation 'com.smartthings.sdk:smartapp-core:0.0.4-PREVIEW'
```

</details>
<br>

If you prefer, the artifacts can be downloaded directly from [Maven Central](https://search.maven.org/search?q=g:com.smarrthings.sdk).

## Examples

Several simple, runnable examples of using the SDK are included in the examples directory.

#### [kotlin-smartapp](examples/kotlin-smartapp) ([Documentation](examples/kotlin-smartapp/README.md)) ![kotlin-logo](docs/kotlin-logo.png) ![ktor-logo](docs/ktor-logo.png)
This Kotlin example implements the Java `smartapp-core` library with a simple [Ktor](https://ktor.io/) server.

#### [java-ratpack-guice-smartapp](examples/java-ratpack-guice-smartapp) ([Documentation](examples/java-ratpack-guice-smartapp/README.md)) ![java-logo](docs/java-logo.png) ![ratpack-logo](docs/ratpack-logo.png)
This Java example implements the Java `smartapp-core` library with a Ratpack server and uses Guice for dependency management.

#### [java-springboot-smartapp](examples/java-springboot-smartapp) ([Documentation](examples/java-springboot-smartapp/README.md)) ![java-logo](docs/java-logo.png) ![spring-logo](docs/spring-logo.png)
This Java example implements the Java `smartapp-core` library using Spring Boot.

#### [java-lambda-smartapp](examples/java-lambda-smartapp) ([Documentation](examples/java-lambda-smartapp/README.md)) ![java-logo](docs/java-logo.png) ![aws-logo](docs/aws-logo.png)
This Java example implements the Java `smartapp-core` library as an AWS Lambda.

## More about SmartThings

Check out our complete developer documentation [here](https://developer-preview.smartthings.com).

To create and manage your services and devices on SmartThings, create an account in the
[developer workspace](https://devworkspace.developer.samsung.com/).

The [SmartThings Community](https://community.smartthings.com/c/developers/) is a good place share and
ask questions.

There is also a [SmartThings reddit community](https://www.reddit.com/r/SmartThings/) where you
can read and share information.

## License and Copyright

Licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)

Copyright 2019 SmartThings, Inc.
