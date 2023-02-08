package com.example.demo.config

data class LoginDto(
    val name: String,
    var password: String
) {
    constructor(): this("", "")
}