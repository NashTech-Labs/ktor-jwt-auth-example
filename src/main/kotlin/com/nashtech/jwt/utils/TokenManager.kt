package com.nashtech.jwt.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.nashtech.jwt.models.User
import io.ktor.server.config.*
import java.util.*

/**
 * Utility class for managing JWT tokens.
 * This class provides functionality to generate and verify JWT tokens for user authentication.
 *
 * Manages JWT tokens for user authentication.
 *
 */
class TokenManager(val config: HoconApplicationConfig) {  // obj create for tokenMangaer

    val audience = config.property("audience").getString()
    val secret = config.property("secret").getString()
    val issuer = config.property("issuer").getString()
    val expirationDate = System.currentTimeMillis() + 600000;

    /**
     * Generates a JWT token for the given user.
     * @param user The user for whom the token is generated.
     * @return The generated JWT token.
     *
     */
    fun generateJWTToken(user: User) :String{

        val token = JWT.create()         // created actual token
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username",user.username)   // save 2 things  username, id
            .withClaim("userId",user.id)

            .withExpiresAt(Date(expirationDate))
            .sign(Algorithm.HMAC256(secret))   //paas the secret to algo.
        return token
    }

    /**
     * Creates a JWT token verifier based on the configured secret, audience, and issuer.
     * @return The JWT token verifier.
     */
    //( verify jwt tokens)......
    fun verifyJWTToken(): JWTVerifier {
        return JWT.require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    }

}