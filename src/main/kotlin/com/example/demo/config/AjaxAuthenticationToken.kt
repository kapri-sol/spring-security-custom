package com.example.demo.config

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority


class AjaxAuthenticationToken(
    private val username: String,
    private val password: String?
) :
    AbstractAuthenticationToken(null) {

    override fun getCredentials(): Any? {
        return password
    }

    override fun getPrincipal(): Any {
        return username
    }
}