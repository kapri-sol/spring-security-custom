package com.example.demo.domain.account;

import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {
    fun findByName(name: String): Account?
}