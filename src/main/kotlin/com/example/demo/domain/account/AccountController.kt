package com.example.demo.domain.account

import com.example.demo.domain.account.dto.CreateAccountDto
import com.example.demo.domain.account.dto.CreateAccountResponse
import com.example.demo.security.ajax.authorization.PathAuthorize
import org.springframework.security.core.parameters.P
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
    @GetMapping("me")
    fun findAccountMe(principal: Principal): String {
        return principal.name
    }

    @GetMapping("{id}")
    @PathAuthorize
    fun findAccount(@P("id") @PathVariable id: Long): Long {
        return id;
    }

    @PostMapping
    fun createAccount(@RequestBody createAccountDto: CreateAccountDto): CreateAccountResponse {
        val accountId = accountService.createAccount(createAccountDto)
        return CreateAccountResponse(
            accountId = accountId
        )
    }
}