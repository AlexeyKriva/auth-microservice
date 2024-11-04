package com.software.modsen.authmicroservice.exceptions

class WrongEmailVerificationCodeException(private val customMessage: String): RuntimeException() {
    override val message: String
        get() = customMessage
}