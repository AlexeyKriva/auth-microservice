package com.software.modsen.authmicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthMicroserviceApplication

fun main(args: Array<String>) {
    runApplication<AuthMicroserviceApplication>(*args)
}
