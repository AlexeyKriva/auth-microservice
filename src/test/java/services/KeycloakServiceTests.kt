package services

import com.software.modsen.authmicroservice.clients.KeycloakClient
import com.software.modsen.authmicroservice.entities.keycloak.*
import com.software.modsen.authmicroservice.services.KeycloakService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.ResponseEntity

@ExtendWith(MockitoExtension::class)
@Disabled
class KeycloakServiceTests {
    @InjectMocks
    lateinit var keycloakService: KeycloakService

    @Mock
    lateinit var keycloakClient: KeycloakClient

    @Test
    fun saveUserInKeycloakTest_ReturnsKeycloakToken() {
        //given
        val username = "username"
        val email = "username@gmail.com"
        val password = "password"
        val roleName = "role"
        val emailVerified = false

        `when`(keycloakClient.getUsersByUsername(anyString(), anyString(), anyString(), isNull()))
            .thenReturn(emptyList())
        `when`(keycloakClient.saveUserInKeycloak(anyString()!!, anyString()!!, any(KeycloakUser::class.java)!!))
            .thenReturn(ResponseEntity.ok(Unit))
        `when`(keycloakClient.getUsersByUsername(anyString(), anyString(), anyString(), isNull()))
            .thenReturn(listOf(mock(KeycloakUserDto::class.java)))
        `when`(keycloakClient.getUserRoles(anyString(), anyString()))
            .thenReturn(listOf(mock(KeycloakRole::class.java)))

        //when
        val token = keycloakService.saveUserInKeycloak(username, email, password, roleName, emailVerified)

        //then
        assertNotNull(token)
        verify(keycloakClient, times(2))
            .getUsersByUsername(anyString(), anyString(), anyString(), isNull())
        verify(keycloakClient).saveUserInKeycloak(anyString(), anyString(), any())
        verify(keycloakClient).getUserRoles(anyString(), anyString())
    }

    @Test
    fun getAccessAndRefreshTokensTest_ReturnsToken() {
        //given
        val username = "username"
        val password = "password"
        val keycloakToken = KeycloakToken("a.b.c", 1, 10, "d.b.e",
            "Bearer ")
        `when`(keycloakClient.getUserToken(anyString(), any()!!)).thenReturn(keycloakToken)

        //when
        val targetKeycloakRole = keycloakService.getAccessAndRefreshTokens("user", "password")

        //then
        assertNotNull(targetKeycloakRole)
    }

    @Test
    fun confirmEmailTest_ReturnsUnit() {
        //given
        val keycloakUserDto = KeycloakUserDto("1", "user", "user", "user",
            "user@gmail.com")
        `when`(keycloakClient.getUsersByUsername(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(listOf(keycloakUserDto))

        //when
        keycloakService.confirmEmail("user@gmail.com")

        //then
        verify(keycloakClient).getUsersByUsername(anyString(), anyString(), anyString(), anyString())
        verify(keycloakClient).confirmEmail(anyString(), anyString(), anyString(), any())
    }
}