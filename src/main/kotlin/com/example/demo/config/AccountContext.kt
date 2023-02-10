package com.example.demo.config

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User


class AccountContext(username: String?, password: String?, authorities: Collection<GrantedAuthority?>?) :
    User(username, password, authorities)