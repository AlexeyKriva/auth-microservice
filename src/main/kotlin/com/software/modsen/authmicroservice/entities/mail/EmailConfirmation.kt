package com.software.modsen.authmicroservice.entities.mail

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class EmailConfirmation(
    @JsonProperty("email")
    @NotNull
    val emailForVerification: String,

    @JsonProperty("verificationCode")
    @NotBlank
    val userVerificationCode: String
)