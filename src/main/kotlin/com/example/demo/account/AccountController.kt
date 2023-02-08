package com.example.demo.account

import com.example.demo.account.dto.CreateAccountDto
import com.example.demo.account.dto.CreateAccountResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RequestMapping("accounts")
@RestController
class AccountController(
    private val accountService: AccountService
) {
    @GetMapping
    fun findAccount(principal: Principal): String {
        return principal.name
    }

    @PostMapping
    fun createAccount(@RequestBody createAccountDto: CreateAccountDto): CreateAccountResponse {
        val accountId = accountService.createAccount(createAccountDto)
        return CreateAccountResponse(
            accountId = accountId
        )
    }
}