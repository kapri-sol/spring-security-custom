package com.example.demo.config

import com.example.demo.account.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class AjaxUserDetailService: UserDetailsService {
    @Autowired private lateinit var accountRepository: AccountRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw IllegalStateException()
        }
        val account = accountRepository.findByName(username) ?: throw ChangeSetPersister.NotFoundException()

        return AccountContext(
            username = account.name,
            password = account.password,
            authorities = arrayListOf(GrantedAuthority { "ROLE_USER" })
        )
    }

}