# SmartThings Client Module

Interact with the SmartThings public REST API with this Java and Android-compatible client module.

## Prerequisites

* Java 1.8+
* A Samsung account

## Adding the Module to Your Build

Include the `smartthings-client` Maven dependency:

```xml
<dependency>
    <groupId>com.smartthings.sdk</groupId>
    <artifactId>smartthings-client</artifactId>
    <version>0.0.4-PREVIEW</version>
</dependency>
```

If you're using Gradle:

```gradle
dependencies {
    compile 'com.smartthings.sdk:smartthings-client:0.0.4-PREVIEW'
}
```

If you do not use Maven or Gradle, jars can be downloaded from the
[central Maven repository](https://search.maven.org/search?q=g:com.smartthings.sdk%20a:smartthings-client).

## Getting Started

### Overview

> NOTE: this project is in an early stage of development and is feature-incomplete

This library makes interacting with the SmartThings API easy and intuitive by:
* providing request/response models
* providing convenience methods
* handling authorization tokens

> Have a feature request, suggestions, comment, or question? Visit the [SmartThings Community](https://community.smartthings.com/) and start a discussion!

Technologies used include:

* Code generation v2 by Swagger
* Feign by Netflix OSS (API methods)
* Jackson by FastXML (models)

#### Android Notes

This client library can be used in Android with SDK 25+

* `compileSdkVersion 25`
* `buildToolsVersion '25.0.2'`
* `minSdkVersion 14`
* `targetSdkVersion 25`
* `sourceCompatibility 1.8`
* `targetCompatibility 1.8`

### Examples

#### Finding Switches

Using Kotlin:
```kotlin
// Build API client
val api = ApiClient()

// Build a client for Devices
val devicesApi = api.buildClient(DevicesApi::class.java)

// Get and list all devices with the capability of Switch
val data = it.installData
val devices = devicesApi.getDevices(data.authToken, mapOf("capabilities" to listOf("switch")))
println(devices)
```

#### Subscriptions

For more information on SmartApp subscriptions, visit the [SmartApp developer documentation](https://developer-preview.smartthings.com/docs/connected-services/subscriptions).

Using Kotlin:
```kotlin
// In the context of a SmartApp LIFECYCLE event (INSTALL or UPDATE)

// Build API client
val api = ApiClient()
// Build a client for Subscriptions
val subscriptionsApi = api.buildClient(SubscriptionsApi::class.java)
val auth = "Bearer ${this.authToken}"

// Clear subscriptions to re-add them
subscriptionsApi.deleteAllSubscriptions(this.installedApp.installedAppId, auth, emptyMap())

// Iterate all devices returned in the InstalledApp's config for key "selectedSwitches"
// That key is defined in Configuration.kt
val devices = this.installedApp.config["selectedSwitches"]
devices?.forEach { switchesConfig ->
    if (switchesConfig.valueType == ConfigEntry.ValueTypeEnum.DEVICE) {
        val subscriptionRequest = SubscriptionRequest().apply {
            sourceType = SubscriptionSource.DEVICE
            device = DeviceSubscriptionDetail().apply {
                deviceId = switchesConfig.deviceConfig.deviceId
                componentId = switchesConfig.deviceConfig.componentId
                capability = "switch"
                attribute = "switch"
                stateChangeOnly = true
                value = "*"
            }
        }

        // Create subscription for the device
        val subscription = subscriptionsApi.saveSubscription(this.installedApp.installedAppId, null, subscriptionRequest)
        println("Creating subscription for ${subscription.id}")
    }
}
```

### Reference Documentation

Find reference documentation for this module [here](https://smartthingscommunity.github.io//smartapp-sdk-java/smartthings-client/javadoc/).

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
