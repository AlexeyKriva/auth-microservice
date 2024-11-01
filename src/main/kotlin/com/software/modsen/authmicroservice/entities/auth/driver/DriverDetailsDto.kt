package com.software.modsen.authmicroservice.entities.auth.driver

import com.fasterxml.jackson.annotation.JsonProperty

data class DriverDetailsDto (
    @JsonProperty("name")
    val username: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("phoneNumber")
    val phoneNumber: String,

    @JsonProperty("sex")
    val sex: Sex,

    @JsonProperty("carId")
    val carId: Long
)