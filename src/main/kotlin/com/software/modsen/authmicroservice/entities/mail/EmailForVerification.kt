package com.software.modsen.authmicroservice.entities.mail

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class EmailForVerification (
    @NotBlank
    @Email
    val email: String
)