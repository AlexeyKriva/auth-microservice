package com.software.modsen.authmicroservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain
import java.util.stream.Stream

@Configuration
class SecurityConfig {
    @Bean
    fun httpSecurity(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf {csrf -> csrf.disable()}
            //.oauth2ResourceServer { oauth2 -> oauth2.jwt(Customizer.withDefaults()) }
            .authorizeHttpRequests { httpRequest -> httpRequest
                .anyRequest().permitAll()
            }
            .build()
    }

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val converter = JwtAuthenticationConverter()
        val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()

        converter.setPrincipalClaimName("preferred_username")
        converter.setJwtGrantedAuthoritiesConverter { jwt ->
            var authorities = jwtGrantedAuthoritiesConverter.convert(jwt) ?: emptyList()
            var roles = jwt.getClaimAsStringList("cab_roles") ?: emptyList()

            return@setJwtGrantedAuthoritiesConverter Stream.concat(authorities.stream(),
                roles.stream()
                    .filter { role -> role.startsWith("ROLE_") }
                    .map { role -> SimpleGrantedAuthority(role) as GrantedAuthority })
                .toList()
        }

        return converter
    }
}