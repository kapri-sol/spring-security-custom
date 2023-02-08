package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfiguration {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

//    @Bean
//    fun authenticationProvider(): AuthenticationProvider {
//        return AjaxAuthenticationProvider()
//    }
//
//    @Bean
//    fun userDetailService(): UserDetailsService {
//        val userBuilder = User.builder()
//        val password = passwordEncoder().encode("1")
//        val manager = InMemoryUserDetailsManager()
//        manager.createUser(userBuilder.username("a").password(password).roles("USER").build())
//        manager.createUser(userBuilder.username("b").password(password).roles("USER").build())
//        return manager
//    }


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .authorizeHttpRequests()
            .requestMatchers( HttpMethod.POST ,"/accounts/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable()
            .formLogin()
            .and()
            .addFilterBefore(
                AjaxLoginProcessingFilter(AntPathRequestMatcher("/auth/login")), UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }
}