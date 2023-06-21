package com.nashtech.jwt.models

import org.junit.Test
import org.mindrot.jbcrypt.BCrypt
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserCredentialsTest {

    @Test
    fun testHashedPassword() {
        // Arrange
        val username = "testuser"
        val password = "testpassword"
        val userCredentials = UserCredentials(username, password)

        // Act
        val hashedPassword = userCredentials.hashedPassword()

        // Assert
        assertTrue(BCrypt.checkpw(password, hashedPassword))
    }

    @Test
    fun testValidCredentials() {
        // Arrange
        val username = "testuser"
        val password = "testpassword"
        val userCredentials = UserCredentials(username, password)

        // Act
        val isValid = userCredentials.isValidCredentials()

        // Assert
        assertTrue(isValid)
    }

    @Test
    fun testInvalidUsername() {
        // Arrange
        val username = "us"
        val password = "testpassword"
        val userCredentials = UserCredentials(username, password)

        // Act
        val isValid = userCredentials.isValidCredentials()

        // Assert
        assertFalse(isValid)
    }

    @Test
    fun testInvalidPassword() {
        // Arrange
        val username = "testuser"
        val password = "pass"
        val userCredentials = UserCredentials(username, password)

        // Act
        val isValid = userCredentials.isValidCredentials()

        // Assert
        assertFalse(isValid)
    }
}
