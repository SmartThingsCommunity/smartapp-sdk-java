package app

import app.routes.smartappExecution
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.JacksonConverter
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route

fun Application.main() {
    // This adds automatically Date and Server headers to each response, and would allow you to configure
    // additional headers served to each response.
    install(DefaultHeaders)
    // This uses use the logger to log every call (request/response)
    install(CallLogging)
    // Automatic '304 Not Modified' Responses
    install(ConditionalHeaders)
    install(DataConversion)
    install(ContentNegotiation) {
        jackson {
            // Don't fail in case the existing model is missing added properties
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            // Exclude null value fields for brevity
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
        }
        register(ContentType.Application.Json, JacksonConverter())
    }
    install(Routing) {
        get("/") {
            call.respond(HttpStatusCode.OK, "You should not be here")
        }
        route("/smartapp") {
            smartappExecution()
        }
    }
}
