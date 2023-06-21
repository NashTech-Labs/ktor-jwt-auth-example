package com.nashtech.jwt.plugins

import com.nashtech.jwt.routing.authenticationRoutes
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

/**
 *
 * In the below code, the `configureRouting()` function is responsible for configuring the routing in the Ktor application.
 * The function is annotated with `Application`, indicating that it belongs to the Ktor application module.
 * Inside the function, the routing is defined using the `routing` block. In this case, a single route is defined for the root path ("/"), which responds with a "Hello World!" message.
 * The function also calls the `authenticationRoutes()` function to configure the authentication routes. This function is assumed to be defined elsewhere.
 *
 * The `configureRouting()` function plays a crucial role in setting up the routes and their corresponding handlers for the Ktor application.
 *
 * Configures the routing for the Ktor application.
 *
 */
fun Application.configureRouting() {
    routing {
        /**
         * Defines the root route ("/") which responds with a "Hello World!" message.
         */
        get("/") {
            call.respondText("Hello World!")
        }
    }
    /**
     * Configures the authentication routes.
     */
    authenticationRoutes()

}
