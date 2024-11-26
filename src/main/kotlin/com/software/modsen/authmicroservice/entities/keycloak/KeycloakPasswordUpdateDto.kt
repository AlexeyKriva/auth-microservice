package com.software.modsen.authmicroservice.entities.keycloak

data class KeycloakPasswordUpdateDto (
    val credentials: List<KeycloakCredential>
)