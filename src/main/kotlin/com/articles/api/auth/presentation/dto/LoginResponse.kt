package com.articles.api.auth.presentation.dto

data class LoginResponse (
    val id:String,
    val username:String,
    val accessToken:String
)