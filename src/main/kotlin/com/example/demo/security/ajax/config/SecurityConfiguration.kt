package com.example.demo.security.ajax.config

import com.example.demo.security.ajax.authentication.AjaxAuthenticationProvider
import com.example.demo.security.ajax.authentication.AjaxLoginAuthenticationEntryPoint
import com.example.demo.security.ajax.authentication.AjaxLoginProcessingFilter
import com.example.demo.security.ajax.authentication.AjaxUserDetailService
import com.example.demo.security.ajax.handler.AjaxAccessDeniedHandler
import com.example.demo.security.ajax.handler.AjaxAuthenticationFailureHandler
import com.example.demo.security.ajax.handler.AjaxAuthenticationSuccessHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
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
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
class SecurityConfiguration {
    private lateinit var  authenticationConfiguration: AuthenticationConfiguration


    @Autowired
    private fun setAuthenticationConfiguration(authenticationConfiguration: AuthenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        val authenticationManager = authenticationConfiguration.authenticationManager as ProviderManager
        authenticationManager.providers.add(customAuthenticationProvider())
        return authenticationManager
    }

    @Bean
    fun authenticationProcessingFilter(): AbstractAuthenticationProcessingFilter {
        val ajaxLoginProcessingFilter = AjaxLoginProcessingFilter(AntPathRequestMatcher("/auth/login"))
        val contextRepository = HttpSessionSecurityContextRepository()
        ajaxLoginProcessingFilter.setSecurityContextRepository(contextRepository)
        ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManager())
        ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler())
        ajaxLoginProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler())
        return  ajaxLoginProcessingFilter
    }

    @Bean
    fun customAuthenticationProvider(): AuthenticationProvider {
        return AjaxAuthenticationProvider(
            userDetailService = customUserDetailService(),
            passwordEncoder = passwordEncoder()
        )
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

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//            .and()
//            .logout()
//            .deleteCookies("/auth/logout")
//            .and()
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