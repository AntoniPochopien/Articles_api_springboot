package com.articles.api.articles.presentation.dto

import org.springframework.web.multipart.MultipartFile

data class AddArticleRequest(
    val title:String,
    val content:String,
    val images: List<MultipartFile>?,
)