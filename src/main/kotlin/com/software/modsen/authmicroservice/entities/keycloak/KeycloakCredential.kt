package com.software.modsen.authmicroservice.entities.keycloak

data class KeycloakCredential(
    val type: String = "password",
    val value: String,
    val temporary: Boolean
)