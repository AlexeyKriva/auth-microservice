package com.software.modsen.authmicroservice.entities.auth.driver

import com.fasterxml.jackson.annotation.JsonProperty

data class DriverDetailsFromService (
    @JsonProperty("id")
    private val id: Long,

    @JsonProperty("name")
    private val name: String,

    @JsonProperty("email")
    private val email: String,

    @JsonProperty("phoneNumber")
    private val phoneNumber: String,

    @JsonProperty("sex")
    private val sex: Sex,

    @JsonProperty("car")
    private val car: Car,

    @JsonProperty("isDeleted")
    private val isDeleted: Boolean
)