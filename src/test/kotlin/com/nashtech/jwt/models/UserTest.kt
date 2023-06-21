package com.nashtech.jwt.models

import org.junit.Assert.*

import org.junit.Test
import kotlin.test.assertEquals

class UserTest {

    @Test
    fun testUserId() {
        // Arrange
        val id = 1
        val username = "testuser"
        val password = "testpassword"
        val user = User(id, username, password)

        // Act
        val userId = user.id

        // Assert
        assertEquals(id, userId)
    }

    @Test
    fun testUsername() {
        // Arrange
        val id = 1
        val username = "testuser"
        val password = "testpassword"
        val user = User(id, username, password)

        // Act
        val retrievedUsername = user.username

        // Assert
        assertEquals(username, retrievedUsername)
    }

    @Test
    fun testPassword() {
        // Arrange
        val id = 1
        val username = "testuser"
        val password = "testpassword"
        val user = User(id, username, password)

        // Act
        val retrievedPassword = user.password

        // Assert
        assertEquals(password, retrievedPassword)
    }
}
