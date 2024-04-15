package com.articles.api.articles.infrastructure

import com.articles.api.articles.domain.model.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository

interface IArticleRepository : CrudRepository<Article, Int> {
    fun findAll(pageable: Pageable): Page<Article>

    fun findByOwnerId(userId:String, pageable: Pageable): Page<Article>
}