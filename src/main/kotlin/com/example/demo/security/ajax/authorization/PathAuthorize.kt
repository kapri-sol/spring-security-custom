package com.example.demo.security.ajax.authorization

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("#id == principal.id")
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PathAuthorize()
