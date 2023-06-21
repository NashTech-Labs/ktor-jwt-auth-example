package com.nashtech.jwt.routing


import com.nashtech.jwt.db.DatabaseConnection
import com.nashtech.jwt.entities.UserEntity
import com.nashtech.jwt.models.UserCredentials
import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.testing.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.ktorm.dsl.insert
import kotlin.test.assertEquals

class AuthenticationRoutesTest {

    private lateinit var testApp: ApplicationEngine
    private lateinit var databaseConnection: DatabaseConnection

    @Before
    fun setup() {
        testApp = createTestServer()
        databaseConnection = DatabaseConnection
    }

    @After
    fun teardown() {
        testApp.stop(0L, 0L)
    }

    @Test
    fun testRegisterEndpoint_WithValidCredentials_ShouldReturnCreated() {
        withTestApplication() {
            // Arrange
            val request = handleRequest(HttpMethod.Post, "/register") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("""{"username": "john", "password": "password123"}""")
            }

            // Assert
            assertEquals(HttpStatusCode.Created, request.response.status())
        }
    }

    @Test
    fun testRegisterEndpoint_WithInvalidCredentials_ShouldReturnBadRequest() {
        withTestApplication() {
            // Arrange
            val request = handleRequest(HttpMethod.Post, "/register") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("""{"username": "j", "password": "pass"}""")
            }

            // Assert
            assertEquals(HttpStatusCode.BadRequest, request.response.status())
        }
    }

    @Test
    fun testRegisterEndpoint_WithExistingUser_ShouldReturnBadRequest() {
        withTestApplication() {
            // Arrange
            val existingUser = UserCredentials("john", "password123")
            databaseConnection.insertUser(existingUser)

            val request = handleRequest(HttpMethod.Post, "/register") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("""{"username": "john", "password": "password123"}""")
            }

            // Assert
            assertEquals(HttpStatusCode.BadRequest, request.response.status())
        }
    }

    @Test
    fun testLoginEndpoint_WithValidCredentials_ShouldReturnOk() {
        withTestApplication() {
            // Arrange
            val existingUser = UserCredentials("john", "password123")
            databaseConnection.insertUser(existingUser)

            val request = handleRequest(HttpMethod.Post, "/login") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("""{"username": "john", "password": "password123"}""")
            }

            // Assert
            assertEquals(HttpStatusCode.OK, request.response.status())
        }
    }

    @Test
    fun testLoginEndpoint_WithInvalidCredentials_ShouldReturnBadRequest() {
        withTestApplication() {
            // Arrange
            val existingUser = UserCredentials("john", "password123")
            databaseConnection.insertUser(existingUser)

            val request = handleRequest(HttpMethod.Post, "/login") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("""{"username": "john", "password": "incorrect"}""")
            }

            // Assert
            assertEquals(HttpStatusCode.BadRequest, request.response.status())
        }
    }

    private fun createTestServer(): ApplicationEngine {
        return embeddedServer(
            factory = Netty,
            module = {
                testApp.application.dispose()
            }
        ).apply { start() }
    }

    private fun DatabaseConnection.insertUser(user: UserCredentials) {
        // Insert the user into the test database for testing purposes
        db.insert(UserEntity) {
            set(UserEntity.username, user.username)
            set(UserEntity.password, user.hashedPassword())
        }
    }
}
