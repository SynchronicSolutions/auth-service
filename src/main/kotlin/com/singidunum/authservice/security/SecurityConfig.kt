package com.singidunum.authservice.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationEntryPoint: BasicAuthEntryPoint,
    private val userDataServiceImpl: UserDetailsServiceImpl
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .authorizeHttpRequests { authorize ->
                authorize
                .requestMatchers("/register").permitAll()
                .anyRequest().authenticated()
            }
            .httpBasic {
                it.authenticationEntryPoint(authenticationEntryPoint)
            }
        return http.build()
    }

    @Bean
    fun authenticationManager(
        userDetailsService: UserDetailsService,
        passwordEncoder: PasswordEncoder
    ): AuthenticationManager {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder)

        return ProviderManager(authenticationProvider)
    }

    @Bean
    fun userDetailsService(): UserDetailsService = userDataServiceImpl

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder()
}