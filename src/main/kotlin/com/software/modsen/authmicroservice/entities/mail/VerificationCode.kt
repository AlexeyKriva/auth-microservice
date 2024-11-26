package com.software.modsen.authmicroservice.entities.mail

import java.time.LocalDateTime

data class VerificationCode(val code: String, val createdAt: LocalDateTime, val expirationTimeInMinutes: Int = 5)