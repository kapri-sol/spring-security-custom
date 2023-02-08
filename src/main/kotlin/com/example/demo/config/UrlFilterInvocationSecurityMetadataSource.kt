package com.example.demo.config

import org.springframework.security.access.ConfigAttribute
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource

class UrlFilterInvocationSecurityMetadataSource: FilterInvocationSecurityMetadataSource {
    override fun getAttributes(`object`: Any?): MutableCollection<ConfigAttribute> {
        TODO("Not yet implemented")
    }

    override fun getAllConfigAttributes(): MutableCollection<ConfigAttribute> {
        TODO("Not yet implemented")
    }

    override fun supports(clazz: Class<*>?): Boolean {
        TODO("Not yet implemented")
    }
}