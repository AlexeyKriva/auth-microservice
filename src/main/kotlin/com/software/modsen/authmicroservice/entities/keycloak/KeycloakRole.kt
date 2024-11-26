package com.software.modsen.authmicroservice.entities.keycloak

import com.fasterxml.jackson.annotation.JsonProperty

data class KeycloakRole(
    @JsonProperty("id")
    val roleId: String,

    @JsonProperty("name")
    val roleName: String
)