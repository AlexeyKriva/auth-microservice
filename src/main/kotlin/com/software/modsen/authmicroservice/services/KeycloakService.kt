package com.software.modsen.authmicroservice.services

import com.software.modsen.authmicroservice.clients.KeycloakClient
import com.software.modsen.authmicroservice.entities.keycloak.*
import com.software.modsen.authmicroservice.exceptions.ExceptionMessage
import com.software.modsen.authmicroservice.exceptions.UserIsAlreadyRegisteredException
import com.software.modsen.authmicroservice.exceptions.RoleNotFoundException
import com.software.modsen.authmicroservice.exceptions.UserNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Service
import org.springframework.scheduling.annotation.Scheduled

@Service
@EnableScheduling
class KeycloakService(private val keycloakClient: KeycloakClient) {
    @Value("\${keycloak.realm}")
    private val keycloakRealm: String = ""

    @Value("\${keycloak.client-id-admin}")
    private val keycloakClientIdAdmin: String = ""

    @Value("\${keycloak.client-id-user}")
    private val keycloakClientIdUser: String = ""

    @Value("\${keycloak.client-secret-user}")
    private val keycloakClientSecretUser: String = ""

    @Value("\${keycloak.admin-username}")
    private val keycloakAdminUsername: String = ""

    @Value("\${keycloak.admin-password}")
    private val keycloakAdminPassword: String = ""

    @Value("\${keycloak.grant-type}")
    private val keycloakGrantType: String = ""

    private var accessToken: String = ""

    fun saveUserInKeycloak(username: String, email: String, password: String, roleName: String,
                           emailVerified: Boolean) :
            KeycloakToken {
        val usersFromKeycloak: List<KeycloakUserDto> = keycloakClient.getUsersByUsername(keycloakRealm,
            accessToken, username, null)

        if (usersFromKeycloak.isEmpty()) {
            val keycloakResponse = keycloakClient
                .saveUserInKeycloak(
                    accessToken, keycloakRealm,
                    KeycloakUser(
                        username, true, username, username, email, emailVerified,
                        listOf(KeycloakCredential(value = password, temporary = false))
                    )
                )

            val savedUsersFromKeycloak: List<KeycloakUserDto> =
                keycloakClient.getUsersByUsername(keycloakRealm, accessToken, username, null)

            val rolesFromKeycloak: List<KeycloakRole> = keycloakClient.getUserRoles(keycloakRealm, accessToken)

            val targetRole = findTargetKeycloakRole(rolesFromKeycloak, roleName)

            keycloakClient.giveRoleToUserInKeycloak(keycloakRealm, accessToken, savedUsersFromKeycloak[0].id,
                listOf(targetRole))

            return getAccessAndRefreshTokens(username, password);
        } else {
            throw UserIsAlreadyRegisteredException(ExceptionMessage().USER_IS_ALREADY_REGISTERED_MESSAGE)
        }
    }

    private fun findTargetKeycloakRole(rolesFromKeycloak: List<KeycloakRole>, targetRoleName: String): KeycloakRole {
        for (keycloakRole in rolesFromKeycloak) {
            if (keycloakRole.roleName.equals(targetRoleName)) {
                return keycloakRole;
            }
        }

        throw RoleNotFoundException(targetRoleName + ExceptionMessage().ROLE_NOT_FOUND_MESSAGE);
    }

    fun getAccessAndRefreshTokens(username: String, password: String): KeycloakToken {
        return keycloakClient.getUserToken(keycloakRealm, buildParametersForGettingUserTokenQuery(
            keycloakClientIdUser, keycloakClientSecretUser, username, password, keycloakGrantType
        ))
    }

    fun confirmEmail(email: String): Unit {
        val usersFromKeycloak: List<KeycloakUserDto> =
            keycloakClient.getUsersByUsername(keycloakRealm, accessToken, null, email)

        keycloakClient.confirmEmail(keycloakRealm, accessToken, usersFromKeycloak[0].id,
            EmailConfirmation(true)
        )
    }

    fun changePassword(username: String, newPassword: String): Unit {
        val usersFromKeycloak: List<KeycloakUserDto> = keycloakClient.getUsersByUsername(keycloakRealm,
            accessToken, username, null)

        println("Before if")

        if (!usersFromKeycloak.isEmpty()) {
            val usersFromKeycloak: List<KeycloakUserDto> = keycloakClient.getUsersByUsername(keycloakRealm,
                accessToken, username, null)

            println("Into if")

            keycloakClient.changeUserPassword(accessToken, keycloakRealm, usersFromKeycloak[0].id,
                KeycloakPasswordUpdateDto(listOf(KeycloakCredential(value = newPassword, temporary = false))))

            return
        }

        println("After if")

        throw UserNotFoundException(ExceptionMessage().USER_NOT_FOUND)
    }

    @Scheduled(fixedRate = 60000)
    private fun refreshAccessToken() {
        val keycloakToken = keycloakClient.getAdminToken(buildParametersForGettingAdminTokenQuery(keycloakClientIdAdmin,
            keycloakAdminUsername, keycloakAdminPassword, keycloakGrantType))

        accessToken = "Bearer ${keycloakToken.accessToken}"
    }

    private fun buildParametersForGettingAdminTokenQuery(keycloakClientId: String, keycloakAdminUsername: String,
                                                         keycloakAdminPassword: String,
                                                         keycloakGrantType: String): Map<String, String> {
        return mapOf(
            "client_id" to keycloakClientId,
            "username" to keycloakAdminUsername,
            "password" to keycloakAdminPassword,
            "grant_type" to keycloakGrantType
        )
    }

    private fun buildParametersForGettingUserTokenQuery(keycloakClientId: String, keycloakClientSecret: String,
                                                        keycloakAdminUsername: String,
                                                         keycloakAdminPassword: String,
                                                         keycloakGrantType: String): Map<String, String> {
        println("keycloakClientId: $keycloakClientId\nkeycloakClientSecret: $keycloakClientSecret\n" +
                "keycloakAdminUsername: $keycloakAdminUsername\nkeycloakAdminPassword: $keycloakAdminPassword\n" +
                "keycloakGrantType: $keycloakGrantType")
        return mapOf(
            "client_id" to keycloakClientId,
            "client_secret" to keycloakClientSecret,
            "username" to keycloakAdminUsername,
            "password" to keycloakAdminPassword,
            "grant_type" to keycloakGrantType
        )
    }
}