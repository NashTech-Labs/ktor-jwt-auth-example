package com.nashtech.jwt.routing

import com.nashtech.jwt.db.DatabaseConnection
import com.nashtech.jwt.entities.UserEntity
import com.nashtech.jwt.models.NoteResponse
import com.nashtech.jwt.models.User
import com.nashtech.jwt.models.UserCredentials
import com.nashtech.jwt.utils.TokenManager
import com.typesafe.config.ConfigFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.*
import org.mindrot.jbcrypt.BCrypt

/**
 * Defines the authentication routes for the Ktor application.
 * This includes registration, login, and authenticated routes.
 * Uses JWT authentication for user login and authorization.
 *
 * Configures the authentication routes for the Ktor application.
 */
fun Application.authenticationRoutes(){

    val dataBase = DatabaseConnection.db
    val tokenManager = TokenManager(HoconApplicationConfig(ConfigFactory.load()))

    routing {

        /**
         * Handles the registration endpoint ("/register").
         * Allows users to register by providing a username and password.
         * Validates the credentials and stores the user information in the database.
         */
        post ("/register"){
            val userCredentials = call.receive<UserCredentials>()

            if (!userCredentials.isValidCredentials()){
                call.respond(HttpStatusCode.BadRequest, NoteResponse(
                    success = false,
                    data = "Username should be >= 3 and password should be >= 6"
                )
                )
                return@post
            }
            //val password = userCredentials.password
            val username = userCredentials.username.toLowerCase()
            val password = userCredentials.hashedPassword()

            /**
             * Check validation Whether user already exist
             */
           val user = dataBase.from(UserEntity)
                .select()
                .where{ UserEntity.username eq username}
                .map { it[UserEntity.username] }
                .firstOrNull()
            if (user != null ){
               call.respond(HttpStatusCode.BadRequest, NoteResponse(
                   success = false,
                   data = "User Already Exist,Plz try to register different user")
               )
                return@post
            }


            dataBase.insert(UserEntity){
                set(it.username,username)
                set(it.password,password)
            }
            call.respond(HttpStatusCode.Created, NoteResponse(
                success = true,
                data = "User has been successfully created"
            )
            )
        }
        /**
         * Handles the login endpoint ("/login").
         * Allows users to authenticate by providing a username and password.
         * Validates the credentials against the stored user information in the database.
         * Generates a JWT token for the authenticated user.
         */
        // JWT login
        post ("/login"){

            val userCredentials = call.receive<UserCredentials>()
            if (!userCredentials.isValidCredentials()){
                call.respond(HttpStatusCode.BadRequest, NoteResponse(
                    success = false,
                    data = "Username should be >= 3 and password should be >= 6"
                )
                )
                return@post
            }
            val username = userCredentials.username.toLowerCase()
            val password = userCredentials.password

            // Check User if Exist
            val user = dataBase.from(UserEntity)
                .select()
                .where{ UserEntity.username eq username}
                .map {
                    val id       = it[UserEntity.id] !!
                    val username = it[UserEntity.username] !!
                    val password = it[UserEntity.password] !!
                    User(id,username,password)
                }.firstOrNull()

            if (user == null ){
                call.respond(HttpStatusCode.BadRequest, NoteResponse(
                    success = false,
                    data = "Invalid username or password")
                )
                return@post
            }
            val doesPasswordMatch = BCrypt.checkpw(password, user?.password)
            if (!doesPasswordMatch){
                call.respond(HttpStatusCode.BadRequest, NoteResponse(
                    success = false,
                    data = "Invalid username or password")
                )
                return@post
            }

            // pass the token from here
            val  token = tokenManager.generateJWTToken(user)
            call.respond(HttpStatusCode.OK, NoteResponse(
                success = true,
                //data = "user logged successfully "
                data = token
            )
            )
        }
        /**
         * Handles the authenticated route ("/me").
         * Requires authentication using JWT.
         * Responds with the username and user ID of the authenticated user.
         */
        authenticate{
            get ("/me"){
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val userId = principal!!.payload.getClaim("userId").asInt()
                call.respondText("Hello, $username with id: $userId")

            }
        }
    }
}