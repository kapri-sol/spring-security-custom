package com.example.demo.config

import com.example.demo.account.dto.CreateAccountDto
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.jaas.AuthorityGranter
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher

class AjaxLoginProcessingFilter(
    requiresAuthenticationRequestMatcher: RequestMatcher?
) : AbstractAuthenticationProcessingFilter(requiresAuthenticationRequestMatcher) {

    private val objectMapper = ObjectMapper()

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val loginDto = objectMapper.readValue(request?.reader, LoginDto::class.java)
        val ajaxAuthenticationToken = AjaxAuthenticationToken(loginDto.name, loginDto.password)
        return authenticationManager.authenticate(ajaxAuthenticationToken)
    }
}