package com.software.modsen.authmicroservice.exceptions

class RoleNotFoundException(private val customMessage: String): RuntimeException() {
    override val message: String
        get() = customMessage
}