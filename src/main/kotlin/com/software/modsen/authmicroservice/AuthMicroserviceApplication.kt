package com.software.modsen.authmicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableFeignClients
@EnableWebMvc
class AuthMicroserviceApplication

fun main(args: Array<String>) {
    runApplication<AuthMicroserviceApplication>(*args)
}