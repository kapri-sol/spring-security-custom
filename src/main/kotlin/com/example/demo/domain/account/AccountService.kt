package com.example.demo.domain.account

import com.example.demo.domain.account.dto.CreateAccountDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val passwordEncoder: PasswordEncoder,
    private val accountRepository: AccountRepository
) {
    fun createAccount(createAccountDto: CreateAccountDto): Long {
        val account =  accountRepository.save(
            Account(
                name = createAccountDto.name,
                password = passwordEncoder.encode(createAccountDto.password)
            )
        )
        return account.id ?: throw IllegalStateException()
    }
}