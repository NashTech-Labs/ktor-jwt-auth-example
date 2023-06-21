package com.nashtech.jwt.models

import kotlinx.serialization.Serializable

/**
 *
 * Represents a generic response for a note.
 * This class is used to deserialize the response received from the server.
 * @param <T> The type of the data in the response.
 * @property data The data contained in the response.
 * @property success Indicates whether the request was successful or not.
 *
 */
@Serializable
data class NoteResponse<T>(

    /**
     * The class has two properties:
     * - `data`: Represents the data contained in the response. It is of type `T`, which is a generic type parameter.
     * - `success`: Indicates whether the request was successful or not. It is a boolean value.
     *
     */
    val data: T,
    val success: Boolean

)
