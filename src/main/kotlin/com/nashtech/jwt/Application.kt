package com.nashtech.jwt


import com.nashtech.jwt.plugins.configureRouting
import com.nashtech.jwt.utils.TokenManager
import com.typesafe.config.ConfigFactory
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

/**
 * Main class for starting the JWT application server.
 * This class configures and starts the server with the required plugins and settings.
 * Main entry point for the JWT application.
 */
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        val config = HoconApplicationConfig(ConfigFactory.load())   // part 2 for verifier
        val tokenManager = TokenManager(config)

        install(Authentication) {
            jwt {

                verifier(tokenManager.verifyJWTToken())
                realm = config.property("realm").getString()
                validate { jwtCredential ->
                    if (jwtCredential.payload.getClaim("username") // just checking 1 thing username for verification
                            .asString().isNotEmpty()) {
                        JWTPrincipal(jwtCredential.payload)
                    } else {
                        null
                    }
                }
            }
        }
        install(ContentNegotiation) {
            json()
        }
        configureRouting()


    }.start(wait = true)

}

