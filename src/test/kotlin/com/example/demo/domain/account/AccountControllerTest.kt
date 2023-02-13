package com.example.demo.domain.account

import com.example.demo.domain.account.dto.CreateAccountDto
import com.example.demo.domain.account.dto.CreateAccountResponse
import com.example.demo.util.WithMockCustomUser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@AutoConfigureMockMvc
@SpringBootTest
class AccountControllerTes(
    @Autowired private val mockMvc: MockMvc
) {

    @Test
    @DisplayName("GET accounts/me")
    @WithMockUser(username = "user")
    fun findAccountMe() {
        mockMvc.perform(
            get("/accounts/me")
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().string("user"))
    }

    @Test
    @DisplayName("GET accounts/1 OK")
    @WithMockCustomUser(id = 1L)
    fun findAccount() {
        mockMvc.perform(
            get("/accounts/1")
        )
            .andExpect(status().isOk)
    }

    @Test
    @DisplayName("GET accounts/1 FORBIDDEN")
    @WithMockCustomUser(id = 2L)
    fun findAccountNotAuthorized() {
        mockMvc.perform(
            get("/accounts/1")
        ).andExpect(status().isForbidden)
    }

    @Test
    @DisplayName("POST /accounts")
    fun createAccount() {
        val createAccountDto = CreateAccountDto(
            name = "a",
            password = "1"
        )

        val createAccountResponse = CreateAccountResponse(
            accountId = 1L
        )

        mockMvc.perform(
            post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(createAccountDto))
        )
            .andExpect(status().isOk)
            .andExpect(content().json(jacksonObjectMapper().writeValueAsString(createAccountResponse)))
    }
}