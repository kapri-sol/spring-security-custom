package com.example.demo.account

import org.springframework.security.access.AccessDecisionManager
import org.springframework.security.access.SecurityMetadataSource
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.stereotype.Component
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.core.Authentication
import java.util.function.Supplier

//@Component
class AccountAuthorizationManager(
    private val accessDecisionManager: AccessDecisionManager,
    private val securityMetadataSource: SecurityMetadataSource
): AuthorizationManager<Any> {
    override fun check(authentication: Supplier<Authentication>?, `object`: Any?): AuthorizationDecision? {
        val attributes = securityMetadataSource.getAttributes(`object`)
        accessDecisionManager.decide(authentication?.get(), `object`, attributes)
        return AuthorizationDecision(true)
    }

}