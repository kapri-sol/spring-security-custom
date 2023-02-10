package com.example.demo.config

import com.example.demo.account.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

class CustomUserDetailsService: UserDetailsService {
    @Autowired private lateinit var accountRepository: AccountRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw IllegalStateException()
        }

        val account = accountRepository.findByName(username) ?: throw NotFoundException()
        return User.builder().username(username).password(account.password).roles("USER").build()
    }
}