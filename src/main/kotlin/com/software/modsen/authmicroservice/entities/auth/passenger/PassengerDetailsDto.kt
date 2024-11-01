package com.software.modsen.authmicroservice.entities.auth.passenger

import com.fasterxml.jackson.annotation.JsonProperty

data class PassengerDetailsDto(
    @JsonProperty("name")
    val username: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("phoneNumber")
    val phoneNumber: String
)