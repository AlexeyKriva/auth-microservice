package com.software.modsen.authmicroservice.exceptions

class EmailVerificationCodeHasExpiredException(private val customMessage: String): RuntimeException() {
    override val message: String
        get() = customMessage
}