package app.routes

import app.handlers.Configuration
import app.handlers.Update
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import com.smartthings.sdk.client.ApiClient
import com.smartthings.sdk.client.methods.DevicesApi
import com.smartthings.sdk.smartapp.core.Response
import com.smartthings.sdk.smartapp.core.SmartApp
import com.smartthings.sdk.smartapp.core.models.EventResponseData
import com.smartthings.sdk.smartapp.core.models.InstallResponseData
import com.smartthings.sdk.smartapp.core.models.UninstallResponseData

val api = ApiClient()

/**
 * The declaration of the SmartApp handlers.
 *
 * See the documentation on what a SmartApp is, what its
 * responsibilities are, and what you can do with them.
 *
 * @see <a href="https://smartthings.developer.samsung.com/develop/guides/smartapps/basics.html">SmartApp Basics</a>
 */
val smartApp: SmartApp = SmartApp.of { spec ->
    spec
        .configuration(Configuration())
        .install {
            val devicesApi = api.buildClient(DevicesApi::class.java)
            val data = it.installData
            val devices = devicesApi.getDevices(data.authToken, mapOf("capabilities" to listOf("switch")))
            println(devices)

            Response.ok(InstallResponseData())
        }
        .update(Update(api))
        .event {
            val data = it.eventData
            println(data)

            Response.ok(EventResponseData())
        }
        .uninstall {
            // TODO: clean up data when user requests installedApp removal
            Response.ok(UninstallResponseData())
        }
}

/**
 * Register the method handlers for /smartapp
 */
fun Route.smartAppExecution() {
    // Uses the location feature to register a get route for '/smartapp'.
    post {
        /**
         * Execute the SmartApp lifecycle handlers
         *   Method A) Using variables - Kotlin cannot infer the body's deserialization type
         *     val body: ExecutionRequest = call.receive()
         *     val execution = smartApp.execute(body)
         *     call.respond(execution)
         */


        /**
         * Execute the SmartApp lifecycle handlers
         *   Method B) inline function calls
         *     Kotlin can automatically infer the types required,
         *     making this code much more succinct.
         */
        call.respond(smartApp.execute(call.receive()))
    }
}
