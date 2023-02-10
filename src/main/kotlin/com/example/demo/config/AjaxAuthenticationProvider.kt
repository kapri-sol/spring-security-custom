package com.example.demo.config

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder

class AjaxAuthenticationProvider(
    private val userDetailService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder
): AuthenticationProvider {


    override fun authenticate(authentication: Authentication?): Authentication {
        if (authentication == null) {
            throw IllegalStateException()
        }

        val username = authentication.name
        val password = authentication.credentials as String

        val accountContext = userDetailService.loadUserByUsername(username)

        if (!passwordEncoder.matches(password, accountContext.password)) {
            throw BadCredentialsException("BadCredentialsException")
        }

        return AjaxAuthenticationToken(
            accountContext.username,
            null,
            accountContext.authorities
        )
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication?.equals(AjaxAuthenticationToken::class.java) ?: throw IllegalStateException()
    }
}