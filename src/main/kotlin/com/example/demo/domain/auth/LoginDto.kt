package com.example.demo.domain.auth

data class LoginDto(
    val name: String,
    var password: String
) {
    constructor(): this("", "")
}