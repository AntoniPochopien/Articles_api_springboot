package com.articles.api.auth.service

import com.articles.api.config.JwtProperties
import com.articles.api.config.TokenService
import com.articles.api.user.domain.service.CustomUserDetailsService
import com.articles.api.user.domain.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AuthService(
    private val authManager: AuthenticationManager,
    private val userService: UserService,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
) {

    fun authenticate(username: String, password: String): Triple<String?, String?, String?> {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                username,
                password
            )
        )
        val user = userService.findUserByUsername(username)
        val userDetails = userDetailsService.loadUserByUsername(username)
        val accessToken = generateAccessToken(userDetails)
        return if (user != null) {
            Triple(user.id, user.username, accessToken)
        } else {
            Triple(null, null, null)
        }
    }

    private fun generateAccessToken(userDetails: UserDetails) =
        tokenService.generate(
            userDetails,
            Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
        )
}