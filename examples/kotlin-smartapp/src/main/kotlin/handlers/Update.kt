package app.handlers

import smartthings.sdk.client.ApiClient
import smartthings.sdk.client.methods.SubscriptionsApi
import smartthings.sdk.client.models.DeviceSubscriptionDetail
import smartthings.sdk.client.models.SubscriptionRequest
import smartthings.sdk.client.models.SubscriptionSource
import smartthings.sdk.smartapp.core.Response
import smartthings.sdk.smartapp.core.extensions.UpdateHandler
import smartthings.sdk.smartapp.core.models.ConfigEntry
import smartthings.sdk.smartapp.core.models.ExecutionRequest
import smartthings.sdk.smartapp.core.models.ExecutionResponse
import smartthings.sdk.smartapp.core.models.UpdateResponseData

class Update(var api: ApiClient) : UpdateHandler {

    override fun handle(request: ExecutionRequest?): ExecutionResponse {
        // If not null, execute and return ExecutionResponse()
        request?.let {
            it.updateData.run {
                // Build API client
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
                        val subscription = subscriptionsApi.saveSubscription(this.installedApp.installedAppId, auth, subscriptionRequest)
                        println("Creating subscription for ${subscription.id}")
                    }
                }
            }
            // Server expects empty Update response data
            println("Responding to the server")
            return Response.ok(UpdateResponseData())
        } ?: return Response.notFound()
        // If null, return ExecutionResponse of "not found"
    }
}
