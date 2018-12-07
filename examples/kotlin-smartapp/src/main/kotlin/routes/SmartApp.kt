package app.routes

import app.handlers.Configuration
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import smartthings.sdk.smartapp.core.Response
import smartthings.sdk.smartapp.core.SmartApp
import v1.smartapps.*

val smartApp: SmartApp = SmartApp.of { spec ->
    spec
        .configuration(Configuration())
        .install {
            // TODO: subscribe on installation
            Response.ok(InstallResponseData())
        }
        .update {
            // TODO: Clear and re-subscribe on reconfiguration
            Response.ok(UpdateResponseData())
        }
        .event {
            // TODO: do something with the events for subscribed devices
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
fun Route.smartappExecution() {
    // Uses the location feature to register a get route for '/smartapp'.
    post {
        // Execute the SmartApp lifecycle handlers
        call.respond(smartApp.execute(call.receive()))
    }
}
