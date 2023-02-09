package com.example.demo.config

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.SpringSecurityCoreVersion
import org.springframework.util.Assert


class AjaxAuthenticationToken(
    private val principal: Any?,
    private var credentials: Any?,
    private val authorities: Collection<GrantedAuthority?>?
) : AbstractAuthenticationToken(authorities) {
    constructor(principal: String, credentials: String) : this(principal, credentials, null)

    private val serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID

    init {
        if (authorities != null) {
            super.setAuthenticated(true)
        } else {
            super.setAuthenticated(false)
        }
    }

    fun unauthenticated(principal: Any?, credentials: Any?): AjaxAuthenticationToken? {
        return AjaxAuthenticationToken(principal, credentials, null)
    }

    fun authenticated(
        principal: Any?, credentials: Any?,
        authorities: Collection<GrantedAuthority?>?
    ): AjaxAuthenticationToken? {
        return AjaxAuthenticationToken(principal, credentials, authorities)
    }

    override fun getCredentials(): Any? {
        return credentials
    }

    override fun getPrincipal(): Any? {
        return principal
    }

    @Throws(IllegalArgumentException::class)
    override fun setAuthenticated(isAuthenticated: Boolean) {
        Assert.isTrue(
            !isAuthenticated,
            "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead"
        )
        super.setAuthenticated(false)
    }

    override fun eraseCredentials() {
        super.eraseCredentials()
        credentials = null
    }

}