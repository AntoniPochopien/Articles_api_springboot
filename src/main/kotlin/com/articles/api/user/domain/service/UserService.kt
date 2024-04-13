package com.articles.api.user.domain.service

import com.articles.api.user.domain.model.User
import com.articles.api.user.infrastructure.IUserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val iUserRepository: IUserRepository, private val encoder: PasswordEncoder) {
    fun createUser(user: User) {
        val encryptedPassword = encoder.encode(user.password)
        val encryptedUser = user.apply { password = encryptedPassword}
        iUserRepository.save(encryptedUser)
    }
    fun findUserByUsername(username: String) = iUserRepository.findUserByUsername(username)
}