package com.example.demo.security.ajax.authentication

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User


class AccountContext(
    username: String?,
    password: String?,
    authorities: Collection<GrantedAuthority?>?,
    val id: Long?) : User(username, password, authorities)