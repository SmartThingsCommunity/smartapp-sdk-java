# smartthings-client

Interact with the SmartThings public REST API with this _Java & Android-compatible_ client library.

## Overview

> NOTE: this project is in early stages of development and is feature-incomplete

This library aims to make interacting with the SmartThings API easy and intuitive by providing: 
* request/response models
* convenience methods
* handling authorization tokens

If you have feature requests, suggestions, comments, or questions, please feel free to open an issue and start a discussion!

### Technologies used

* Code generation v2 by Swagger
* Feign by Netflix OSS (API methods)
* Jackson by FastXML (models) 

## Developers

### Requirements

* Java 1.8+
* SmartThings developer account

#### Android usage

This client library can be used in Android with SDK 25+
* `compileSdkVersion 25`
* `buildToolsVersion '25.0.2'`
* `minSdkVersion 14`
* `targetSdkVersion 25`
* `sourceCompatibility 1.8`
* `targetCompatibility 1.8`

### Installation

More information on this topic is coming when the first release is published to a repository.

#### Code generation

By default, the necessary models are generated with **swagger-codegen v2** based on the public specification. If you'd like, you can overwrite the existing `resources/st-api.yml`.

### Usage

#### Basics

_Figure A_
1. Create an instance of the ApiClient. 
2. Build a client for Devices.
3. List all devices with the capability of Switch.

_Figure B_
1. Create an instance of the ApiClient. 
2. Build a client for Subscriptions.
3. Iterate over all devices authorized by the SmartApp to create event subscriptions.

For more information on the SmartThings API reference (request/response expectations) please [view the API reference documentation](https://smartthings.developer.samsung.com/docs/api-ref/st-api.html).  

_Fig. A_ List all Switch devices 
```kotlin
// Build API client
val api = ApiClient()
val devicesApi = api.buildClient(DevicesApi::class.java)
val data = it.installData
val devices = devicesApi.getDevices(data.authToken, mapOf("capabilities" to listOf("switch")))
println(devices)
```

_Fig. B_ Subscribe to an event for all devices authorized for a SmartApp 
```kotlin
// In the context of a SmartApp LIFECYCLE event (INSTALL or UPDATE)

// Build API client
val api = ApiClient()
val subscriptionsApi = api.buildClient(SubscriptionsApi::class.java)
val auth = "Bearer ${this.authToken}"

// Clear subscriptions to re-add them
subscriptionsApi.deleteAllSubscriptions(this.installedApp.installedAppId, auth, emptyMap())

// Iterate all devices returned in the InstalledApp's config for key "selectedSwitches"
// That key is defined in Configuration.kt
val devices = this.installedApp.config["selectedSwitches"]
devices?.forEach { switchesConfig ->
    if (switchesConfig.valueType == ConfigEntry.ValueTypeEnum.DEVICE) {
        val deviceId = switchesConfig.deviceConfig.deviceId
        val componentId = switchesConfig.deviceConfig.componentId
        val subscriptionRequest = SubscriptionRequest().apply {
            sourceType = SubscriptionSource.DEVICE
            device = DeviceSubscriptionDetail().apply {
                this.deviceId = deviceId
                this.componentId = componentId
                this.capability = "switch"
                this.attribute = "switch"
                this.isStateChangeOnly = true
                this.value = "*"
            }
        }
        
        // Create subscription for the device
        val subscription = subscriptionsApi.saveSubscription(this.installedApp.installedAppId, null, subscriptionRequest)
        println("Creating subscription for ${subscription.id}")
    }
}
```
