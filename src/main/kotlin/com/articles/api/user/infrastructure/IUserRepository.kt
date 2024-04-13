package com.articles.api.user.infrastructure

import com.articles.api.user.domain.model.User
import org.springframework.data.repository.CrudRepository

interface IUserRepository : CrudRepository<User, Int> {
    fun findUserByUsername(username: String): User?
}