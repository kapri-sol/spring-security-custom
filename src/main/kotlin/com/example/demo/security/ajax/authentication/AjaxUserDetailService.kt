package com.example.demo.security.ajax.authentication

import com.example.demo.domain.account.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AjaxUserDetailService: UserDetailsService {
    @Autowired private lateinit var accountRepository: AccountRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw IllegalStateException()
        }

        val account = accountRepository.findByName(username) ?: throw ChangeSetPersister.NotFoundException()

        return AccountContext(
            id = account.id,
            username = account.name,
            password = account.password,
            authorities = arrayListOf(SimpleGrantedAuthority("USER"))
        )
    }
}