package com.nashtech.jwt.db

import org.ktorm.database.Database

/**
 * Establishes a database connection using the provided connection details from environment variables.
 * The connection details include the database URL, driver class, username, and password.
 *
 * @return The connected Database instance.
 * @throws NullPointerException If any of the environment variables (DB_URL, DB_DRIVER, DB_USER, DB_PASSWORD) are not set.
 */
object DatabaseConnection {
    /**
     * In the above code, the `DatabaseConnection` class is a utility class that establishes a database
     * connection using the KTorm library. It retrieves the connection details
     * (URL, driver, username, and password) from environment variables.
     *
     */
    val db = Database.connect(
        url = System.getenv("DB_URL"),
        driver = System.getenv("DB_DRIVER"),
        user = System.getenv("DB_USER"),
        password = System.getenv("DB_PASSWORD")
    )
}