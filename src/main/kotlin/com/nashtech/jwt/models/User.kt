package com.nashtech.jwt.models

/**
 *
 * In the below code, the `User` class represents a user. It contains information about the user, including their unique identifier (`id`), username (`username`), and password (`password`).
 *
 * This class is a data class, which automatically generates standard implementations of methods such as `equals()`, `hashCode()`, and `toString()`, based on the properties defined in the class.
 *
 * You can use the `User` class to create instances of users and store their information. It provides a convenient way to encapsulate and manipulate user data in your application.
 *
 * Represents a user.
 * @property id The unique identifier of the user.
 * @property username The username of the user.
 * @property password The password of the user.
 *
 */
data class User(
    val id: Int,
    val username: String,
    val password: String
)
