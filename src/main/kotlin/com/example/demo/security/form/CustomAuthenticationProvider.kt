package com.example.demo.security.form

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder

class CustomAuthenticationProvider: AuthenticationProvider {
    @Autowired lateinit var userDetailsService: UserDetailsService
    @Autowired lateinit var passwordEncoder: PasswordEncoder

    override fun authenticate(authentication: Authentication?): Authentication {
        if (authentication == null) {
            throw IllegalStateException()
        }

        val username = authentication.name
        val password = authentication.credentials as String

        val accountContext = userDetailsService.loadUserByUsername(username)

        if (!passwordEncoder.matches(password, accountContext.password)) {
            throw BadCredentialsException("BadCredentials")
        }

        return UsernamePasswordAuthenticationToken(
            accountContext.username,
            null,
            accountContext.authorities
        )
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}