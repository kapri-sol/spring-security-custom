package com.example.demo.domain.account

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Account(
    name: String,
    password: String
) {
    @Id
    @GeneratedValue
    val id: Long? = null

    var name = name
        private set

    var password = password
        private set
}