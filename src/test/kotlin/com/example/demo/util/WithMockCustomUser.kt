package com.example.demo.util

import org.springframework.security.test.context.support.WithSecurityContext

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithMockCustomerUserSecurityContextFactory::class)
annotation class WithMockCustomUser(val username: String = "user", val password: String = "1", val authorities: Array<String> = ["USER"], val id: Long = 1L)