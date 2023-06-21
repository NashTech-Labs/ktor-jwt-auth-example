package com.nashtech.jwt.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.JWTVerifier
import com.nashtech.jwt.models.User
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TokenManagerTest {



    private lateinit var tokenManager: TokenManager

    @Before
    fun setup() {
        val config = HoconApplicationConfig(ConfigFactory.load("application.conf"))
        tokenManager = TokenManager(config)
    }

    @Test
    fun testGenerateJWTToken() {
        // Arrange
        val user = User(id = 1, username = "john","password")

        // Act
        val token = tokenManager.generateJWTToken(user)

        // Assert
        // Add assertions here based on your requirements
        assertEquals("http://0.0.0.0:8080/hello", JWT.decode(token).audience[0])
        assertEquals("http://0.0.0.0:8080/", JWT.decode(token).issuer)
        assertEquals("john", JWT.decode(token).getClaim("username").asString())
        assertEquals(1, JWT.decode(token).getClaim("userId").asInt())
    }

    @Test
    fun testVerifyValidJWTToken() {
        // Arrange
        val user = User(id = 1, username = "john","password")
        val token = tokenManager.generateJWTToken(user)

        // Act
        val verifier: JWTVerifier = tokenManager.verifyJWTToken()

        // Assert
        // Verify that the token is valid without throwing any exceptions
        verifier.verify(token)
    }

    @Test(expected = JWTVerificationException::class)
    fun testVerifyInvalidJWTToken() {
        // Arrange
        val token = "invalid-token"

        // Act
        val verifier: JWTVerifier = tokenManager.verifyJWTToken()

        // Assert
        // Verify that an exception is thrown when verifying an invalid token
        verifier.verify(token)
    }

    // Add more test cases for other methods if needed
}
