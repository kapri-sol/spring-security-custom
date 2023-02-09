package com.example.demo.auth

data class LoginDto(
    val name: String,
    var password: String
) {
    constructor(): this("", "")
}