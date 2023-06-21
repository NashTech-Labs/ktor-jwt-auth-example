package com.nashtech.jwt.models

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

/**
 *
 * In the below code, the `UserCredentials` class represents user credentials. It contains the username and password of a user.
 *
 * This class is annotated with `Serializable` to indicate that it can be serialized/deserialized using Kotlin serialization.
 *
 * The class provides two methods:
 * - `hashedPassword()`: This method uses the BCrypt algorithm to hash the password and returns the hashed password.
 * - `isValidCredentials()`: This method validates the user credentials by checking if the username length is at least 3 characters and the password length is at least 6 characters. It returns `true` if the credentials are valid, and `false` otherwise.
 *
 * The `UserCredentials` class is useful for handling user authentication and storing user login information securely.
 *
 *
 * Represents user credentials.
 * @property username The username of the user.
 * @property password The password of the user
 */
@Serializable
data class UserCredentials(
    val username: String,
    val password: String
){
    /**
     * Hashes the password using BCrypt algorithm.
     *
     * @return The hashed password.
     */
    fun hashedPassword(): String{
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }
    /**
     * Validates the user credentials.
     *
     * @return `true` if the credentials are valid, `false` otherwise.
     */
    fun isValidCredentials(): Boolean{
        return username.length >=3 && password.length >=6
    }
}
