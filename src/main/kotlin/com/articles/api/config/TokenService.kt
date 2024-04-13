package com.articles.api.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.security.core.userdetails.UserDetails
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.Date

@Service
class TokenService(jwtProperties: JwtProperties) {
    private val secretKey = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String =
        Jwts.builder().claims().subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis())).expiration(expirationDate)
            .add(additionalClaims).and().signWith(secretKey).compact()

    fun extractUsername(token: String): String? = getAllClaims(token)?.subject

    fun isExpired(token: String) =
        getAllClaims(token)?.expiration?.before(Date(System.currentTimeMillis())) ?: true

    fun isValid(token: String, userDetails: UserDetails): Boolean {
        val id = extractUsername(token)
        return userDetails.username == id && !isExpired(token)
    }

    private fun getAllClaims(token: String): Claims? {
        val parser = Jwts.parser().verifyWith(secretKey).build()
        return try {
            parser
                .parseSignedClaims(token)
                .payload
        } catch (ex: JwtException) {
            null
        }
    }
}