package com.articles.api.auth.presentation.controller

import com.articles.api.auth.presentation.dto.AuthRequest
import com.articles.api.auth.presentation.dto.LoginResponse
import com.articles.api.auth.service.AuthService
import com.articles.api.user.domain.model.User
import com.articles.api.user.domain.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val userService: UserService, private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@RequestBody authRequest: AuthRequest): ResponseEntity<Boolean> {
        userService.createUser(User(null, authRequest.username, authRequest.password))
        return ResponseEntity(true, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest): ResponseEntity<LoginResponse?> {
       val response = authService.authenticate(authRequest.username, authRequest.password)
        if(response.first != null && response.second != null && response.third != null){
            return ResponseEntity(LoginResponse(response.first!!, response.second!!, response.third!!), HttpStatus.OK)
        }else{
            return ResponseEntity(null, HttpStatus.FORBIDDEN)
        }
    }
}
