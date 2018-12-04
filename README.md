# smartthings-sdk

A set of Java libraries for building SmartApps.
## Modules
### smartapp-core
Core SmartApp framework. Provides abilities for defining a SmartApp that could be used in many environments - AWS Lambda / Dropwizard / Ratpack / etc

### smartapp-guice
An extension library that provides support for building a SmartApp with Guice dependency injection.

### smartthings-client (not yet implemented)
An API library that provides useful utilities for working with the Subscription / Schedules / Device APIs

### smartapp-dropwizard-auth (not yet implemented)
An extension library that provides mechanisms for verifying SmartApp http signatures in a Dropwizard project.

### smartapp-lazybones (not yet implemented)
A templating extension for Lazybones that bootstraps out a basic Dropwizard SmartApp project.

## SmartApp Example

```
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
# To Run / Build
```
./gradlew clean build
```
