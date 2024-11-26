package com.software.modsen.authmicroservice.clients

import com.software.modsen.authmicroservice.config.FormFeignEncoderConfig
import com.software.modsen.authmicroservice.entities.keycloak.*
import feign.Headers
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@FeignClient(name = "keycloak-server", configuration = [FormFeignEncoderConfig::class])
interface KeycloakClient {
    @PostMapping("/admin/realms/{keycloakRealm}/users", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveUserInKeycloak(
        @RequestHeader("Authorization") authHeader: String,
        @PathVariable("keycloakRealm") keycloakRealm: String,
        @RequestBody keycloakUser: KeycloakUser
    ): ResponseEntity<Unit>;

    @PostMapping("/admin/realms/{keycloakRealm}/users/{id}/role-mappings/realm", consumes =
    [MediaType.APPLICATION_JSON_VALUE])
    fun giveRoleToUserInKeycloak(
        @PathVariable("keycloakRealm") keycloakRealm: String,
        @RequestHeader("Authorization") authHeader: String,
        @PathVariable("id") userId: String,
        @RequestBody keycloakRoles: List<KeycloakRole>
    ): ResponseEntity<Unit>;

    @GetMapping("/admin/realms/{keycloakRealm}/users")
    fun getUsersByUsername(
        @PathVariable("keycloakRealm") keycloakRealm: String,
        @RequestHeader("Authorization") authHeader: String,
        @RequestParam(value = "username", required = false) username: String?,
        @RequestParam(value = "email", required = false) email: String?
    ): List<KeycloakUserDto>;

    @PostMapping("/realms/master/protocol/openid-connect/token",
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun getAdminToken(
        params: Map<String, Any>
    ): KeycloakToken

    @PostMapping("/realms/{keycloakRealm}/protocol/openid-connect/token",
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun getUserToken(
        @PathVariable("keycloakRealm") keycloakRealm: String,
        params: Map<String, Any>
    ): KeycloakToken

    @GetMapping("/admin/realms/{keycloakRealm}/roles")
    fun getUserRoles(
        @PathVariable("keycloakRealm") keycloakRealm: String,
        @RequestHeader("Authorization") authHeader: String
    ): List<KeycloakRole>

    @PutMapping("/admin/realms/{keycloakRealm}/users/{id}")
    fun confirmEmail(
        @PathVariable("keycloakRealm") keycloakRealm: String,
        @RequestHeader("Authorization") authHeader: String,
        @PathVariable("id") userId: String,
        @RequestBody emailConfirmation: EmailConfirmation
    ): ResponseEntity<Unit>
}