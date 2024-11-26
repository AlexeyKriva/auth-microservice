package com.software.modsen.authmicroservice.entities.auth.passenger

import com.fasterxml.jackson.annotation.JsonProperty

data class PassengerDetailsFromService (
    @JsonProperty("id")
    private val id: Long,

    @JsonProperty("name")
    private val name: String,

    @JsonProperty("email")
    private val email: String,

    @JsonProperty("phoneNumber")
    private val phoneNumber: String,

    @JsonProperty("isDeleted")
    private val isDeleted: Boolean
)