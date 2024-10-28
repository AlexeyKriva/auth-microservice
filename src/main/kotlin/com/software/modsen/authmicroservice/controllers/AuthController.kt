package com.software.modsen.authmicroservice.controllers

import com.software.modsen.authmicroservice.entities.VerificationCode
import com.software.modsen.authmicroservice.services.MailSenderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
@RequestMapping(value = ["/api/auth"], produces = ["application/json"])
class AuthController(private val mailSenderService: MailSenderService) {
    @PostMapping("/register")
    public fun registration(): Unit {
    }
}