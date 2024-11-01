package com.software.modsen.authmicroservice.clients

import com.software.modsen.authmicroservice.config.FormFeignEncoderConfig
import com.software.modsen.authmicroservice.entities.keycloak.KeycloakRole
import com.software.modsen.authmicroservice.entities.keycloak.KeycloakToken
import com.software.modsen.authmicroservice.entities.keycloak.KeycloakUser
import feign.Headers
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(name = "keycloak-server", configuration = [FormFeignEncoderConfig::class])
interface KeycloakClient {
    @PostMapping("/admin/realms/{keycloakRealm}/users", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveUserInKeycloak(
        @RequestHeader("Authorization") authHeader: String,
        @PathVariable("keycloakRealm") keycloakRealm: String,
        @RequestBody keycloakUser: KeycloakUser
    ): ResponseEntity<Unit>;

    @PostMapping("/admin/realms/cab-agg/users/{id}/role-mappings/realm", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun giveRoleToUserInKeycloak(
        @RequestHeader("Authorization") authHeader: String,
        @PathVariable("id") userId: String,
        @RequestBody keycloakRoles: List<KeycloakRole>
    ): ResponseEntity<Unit>;

    @GetMapping("/admin/realms/cab-agg/users")
    fun getUsersByUsername(
        @RequestHeader("Authorization") authHeader: String,
        @RequestParam("username") username: String
    ): List<Map<String, Any>>;

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
}