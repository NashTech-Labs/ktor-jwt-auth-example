package com.nashtech.jwt.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

/**
 *
 * Represents the UserEntity table in the database.
 * It defines the table structure and column mappings for the "users" table.
 *
 */
object UserEntity: Table<Nothing>("users") {

    /**
     * The primary key column for the "users" table.
     */
    val id        = int("id").primaryKey()
    /**
     * The column for storing the username.
     */
    val username = varchar("username");

    /**
     * The column for storing the password.
     */
    val password = varchar("password");
}