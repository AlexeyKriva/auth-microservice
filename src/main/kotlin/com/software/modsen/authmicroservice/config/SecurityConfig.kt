package com.software.modsen.authmicroservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    public fun httpSecurity(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf {csrf -> csrf.disable()}
            .authorizeHttpRequests { httpRequest -> httpRequest
                .anyRequest().permitAll()
            }
            .build()
    }
}