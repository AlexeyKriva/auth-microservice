package com.software.modsen.authmicroservice.exceptions

class UserIsAlreadyRegisteredException(
    private val customMessage: String
): RuntimeException() {
    override val message: String
        get() = customMessage
}