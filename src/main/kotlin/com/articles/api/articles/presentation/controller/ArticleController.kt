package com.articles.api.articles.presentation.controller

import com.articles.api.articles.domain.service.ArticleService
import com.articles.api.articles.presentation.dto.AddArticleRequest
import com.articles.api.articles.presentation.dto.AddArticleResponse
import com.articles.api.articles.presentation.dto.GetArticlesResponse
import com.articles.api.user.presentation.dto.SimpleUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/articles")
class ArticleController(private val articleService: ArticleService) {

    @PostMapping
    fun addArticle(@ModelAttribute request: AddArticleRequest): AddArticleResponse {
        val title = request.title
        val content = request.content
        val images = request.images
        return articleService.addArticle(title, content, images).let {
            AddArticleResponse(it.id!!, it.title, it.content, it.imagesUrl)
        }
    }

    @GetMapping
    fun getArticles(
        @RequestParam(defaultValue = "0") page: Int
    ): Page<GetArticlesResponse> {
        val articles = articleService.getArticles(page)
        return articles.map {
            GetArticlesResponse(
                it.id!!,
                it.title,
                it.content,
                it.imagesUrl,
                SimpleUser(
                    it.owner.id!!,
                    it.owner.username
                )
            )
        }
    }
}