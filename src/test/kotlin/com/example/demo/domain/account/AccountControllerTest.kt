package com.example.demo.domain.account

import com.example.demo.util.WithMockCustomUser
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
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
    @DisplayName("GET accounts/1")
    @WithMockCustomUser
    fun findAccount() {
        mockMvc.perform(
            get("/accounts/2")
        )
            .andDo(print())
    }

    @Test
    fun createAccount() {
    }
}