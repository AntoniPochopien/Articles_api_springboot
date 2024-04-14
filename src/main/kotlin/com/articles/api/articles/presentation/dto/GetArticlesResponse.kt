package com.articles.api.articles.presentation.dto

import com.articles.api.user.presentation.dto.SimpleUser

data class GetArticlesResponse(
    val id: Int,
    val title: String,
    val content: String,
    val images: List<String>,
    val owner: SimpleUser
)
