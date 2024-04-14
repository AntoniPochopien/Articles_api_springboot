package com.articles.api.articles.presentation.dto

import org.springframework.web.multipart.MultipartFile

data class AddArticleResponse(
    val id:Int,
    val title:String,
    val content:String,
    val images: List<String>,
)