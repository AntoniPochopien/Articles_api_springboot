package com.articles.api.user.presentation.controller

import com.articles.api.user.domain.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController (private val userService: UserService) {

    @GetMapping("/exist")
    fun countUsersByUsername(@RequestParam("username") username: String):Map<String, Boolean>{
        val userExists = userService.findUserByUsername(username) != null
        return mapOf("exist" to userExists)
    }
}