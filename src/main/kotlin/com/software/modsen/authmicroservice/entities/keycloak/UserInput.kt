package com.software.modsen.authmicroservice.entities.keycloak

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserInput (
    @JsonProperty("username")
    @NotBlank(message = "Name cannot be blank.")
    val username: String,

    @JsonProperty("password")
    @NotBlank(message = "Password cannot be blank")
    val password: String
)