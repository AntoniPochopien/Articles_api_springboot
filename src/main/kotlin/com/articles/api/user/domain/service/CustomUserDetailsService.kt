package com.articles.api.user.domain.service

import com.articles.api.user.infrastructure.IUserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.articles.api.user.domain.model.User

@Service
class CustomUserDetailsService (
    private val iUserRepository: IUserRepository
) : UserDetailsService{

    override fun loadUserByUsername(username: String): UserDetails {
        val user =  iUserRepository.findUserByUsername(username)
        if(user != null){
            return user.mapToUserDetails()
        }else{
            throw UsernameNotFoundException("Not found")
        }
    }

    private fun ApplicationUser.mapToUserDetails(): UserDetails = User.builder().username(this.username).password(this.password).build()
}