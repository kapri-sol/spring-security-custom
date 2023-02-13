package com.example.demo.util

import com.example.demo.security.ajax.authentication.AccountContext
import com.example.demo.security.ajax.authentication.AjaxAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory

class WithMockCustomerUserSecurityContextFactory :
    WithSecurityContextFactory<WithMockCustomUser> {
    override fun createSecurityContext(customUser: WithMockCustomUser): SecurityContext {
        val context = SecurityContextHolder.createEmptyContext()
        val accountContext = AccountContext(
            username = customUser.username,
            password = customUser.password,
            authorities = customUser.authorities.map { SimpleGrantedAuthority(it) },
            id = customUser.id
        )

        val ajaxAuthenticationToken = AjaxAuthenticationToken(principal = accountContext, credentials = accountContext.password, authorities = arrayListOf(SimpleGrantedAuthority("USER")))
        context.authentication = ajaxAuthenticationToken
        return context
    }
}