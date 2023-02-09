package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    fun authenticationConfiguration(): AuthenticationConfiguration {
        return AuthenticationConfiguration()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        val authenticationManager = authenticationConfiguration.authenticationManager as ProviderManager
        authenticationManager.providers.add(customAuthenticationProvider())
        return authenticationManager
    }

    @Bean
    fun authenticationProcessingFilter(): AbstractAuthenticationProcessingFilter {
        val ajaxLoginProcessingFilter = AjaxLoginProcessingFilter(AntPathRequestMatcher("/auth/login"))
        ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration()))
        ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler())
        ajaxLoginProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler())
        return  ajaxLoginProcessingFilter
    }
    @Bean
    fun customAuthenticationProvider(): AuthenticationProvider {
        return AjaxAuthenticationProvider()
    }

    @Bean
    fun customUserDetailService(): UserDetailsService {
        return AjaxUserDetailService()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun authenticationSuccessHandler(): AuthenticationSuccessHandler {
        return AjaxAuthenticationSuccessHandler()
    }

    @Bean
    fun authenticationFailureHandler(): AuthenticationFailureHandler {
        return AjaxAuthenticationFailureHandler()
    }

    @Bean
    fun accessDeniedHandler(): AccessDeniedHandler {
        return AjaxAccessDeniedHandler()
    }

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
            .addFilterBefore(
                authenticationProcessingFilter(), UsernamePasswordAuthenticationFilter::class.java
            )
            .exceptionHandling()
            .authenticationEntryPoint(AjaxLoginAuthenticationEntryPoint())
            .accessDeniedHandler(accessDeniedHandler())
            .and()
            .csrf().disable()
            .build()
    }
}