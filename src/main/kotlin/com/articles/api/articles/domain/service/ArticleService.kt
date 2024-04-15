package com.articles.api.articles.domain.service

import com.articles.api.articles.domain.model.Article
import com.articles.api.articles.domain.model.ArticleFilters
import com.articles.api.articles.infrastructure.IArticleRepository
import com.articles.api.images_service.ImagesService
import com.articles.api.user.infrastructure.IUserRepository
import org.hibernate.annotations.Filters
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ArticleService(
    private val iArticleRepository: IArticleRepository,
    private val iUserRepository: IUserRepository,
    private val imagesService: ImagesService,
) {

    fun addArticle(title: String, content: String, images: List<MultipartFile>?): Article {
        val username = SecurityContextHolder.getContext().authentication.name
        val user = iUserRepository.findUserByUsername(username)
        val newArticle = Article(null, title, content, mutableListOf<String>(), user!!)
        user.articles.add(newArticle)
        val savedUser = iUserRepository.save(user)
        val savedArticle = savedUser.articles.last()
        val imagesUrls = mutableListOf<String>()
        images?.forEachIndexed { index, image ->
            imagesUrls.add(imagesService.saveImage(image, savedArticle.id!!, index))
        }
        savedArticle.imagesUrl.addAll(imagesUrls)
        iArticleRepository.save(savedArticle)
        return savedArticle
    }

    fun getArticles(page: Int, filter: ArticleFilters): Page<Article> {
        val pageable: Pageable = PageRequest.of(page, 15)
        return when (filter) {
            ArticleFilters.ALL -> {
                return iArticleRepository.findAll(pageable)
            }
            ArticleFilters.MINE -> {
                val username = SecurityContextHolder.getContext().authentication.name
                val user = iUserRepository.findUserByUsername(username)
                return  iArticleRepository.findByOwnerId(user!!.id!!, pageable)
            }
        }
    }

    fun deleteArticle(id: Int) = iArticleRepository.deleteById(id)
}