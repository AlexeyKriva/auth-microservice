package services

import com.software.modsen.authmicroservice.entities.auth.admin.AdminDetails
import com.software.modsen.authmicroservice.entities.auth.driver.DriverDetails
import com.software.modsen.authmicroservice.entities.auth.driver.Sex
import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetails
import com.software.modsen.authmicroservice.entities.keycloak.KeycloakToken
import com.software.modsen.authmicroservice.entities.keycloak.UserAuthDetails
import com.software.modsen.authmicroservice.observer.driver.DriverSubject
import com.software.modsen.authmicroservice.observer.passenger.PassengerSubject
import com.software.modsen.authmicroservice.services.AuthService
import com.software.modsen.authmicroservice.services.KeycloakService
import com.software.modsen.authmicroservice.services.MailSenderService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class AuthServiceTests {
    @InjectMocks
    lateinit var authService: AuthService

    @Mock
    lateinit var keycloakService: KeycloakService

    @Mock
    lateinit var passengerSubject: PassengerSubject

    @Mock
    lateinit var driverSubject: DriverSubject

    @Mock
    lateinit var mailSenderService: MailSenderService

    @Test
    fun savePassengerInKeycloakTest_ReturnsKeycloakToken() {
        //given
        val passengerDetails = PassengerDetails("username", "email@gmail.com",
            "+375293333333", "password")
        val keycloakToken = KeycloakToken("a.b.c", 1, 10, "d.e.f",
            "Bearer")
        `when`(keycloakService.saveUserInKeycloak(passengerDetails.username, passengerDetails.email,
            passengerDetails.password, "ROLE_PASSENGER", false)).thenReturn(keycloakToken)

        //when
        val keycloakTokenFromAuthService = authService.savePassenger(passengerDetails)

        //then
        assertNotNull(keycloakTokenFromAuthService)
        assertEquals(keycloakTokenFromAuthService.accessToken, keycloakToken.accessToken)
        assertEquals(keycloakTokenFromAuthService.expiresIn, keycloakToken.expiresIn)
        assertEquals(keycloakTokenFromAuthService.refreshExpiresIn, keycloakToken.refreshExpiresIn)
        assertEquals(keycloakTokenFromAuthService.refreshToken, keycloakToken.refreshToken)
        assertEquals(keycloakTokenFromAuthService.tokenType, keycloakToken.tokenType)
        verify(passengerSubject).notifyPassengerObservers(passengerDetails)
    }

    @Test
    fun saveDriverInKeycloakTest_ReturnsKeycloakToken() {
        //given
        val driverDetails = DriverDetails("username", "email@gmail.com",
            "+375293333333", "password", Sex.MALE, 1)
        val keycloakToken = KeycloakToken("a.b.c", 1, 10, "d.e.f",
            "Bearer")
        `when`(keycloakService.saveUserInKeycloak(driverDetails.username, driverDetails.email,
            driverDetails.password, "ROLE_DRIVER", false)).thenReturn(keycloakToken)

        //when
        val keycloakTokenFromAuthService = authService.saveDriver(driverDetails)

        //then
        assertNotNull(keycloakTokenFromAuthService)
        assertEquals(keycloakTokenFromAuthService.accessToken, keycloakToken.accessToken)
        assertEquals(keycloakTokenFromAuthService.expiresIn, keycloakToken.expiresIn)
        assertEquals(keycloakTokenFromAuthService.refreshExpiresIn, keycloakToken.refreshExpiresIn)
        assertEquals(keycloakTokenFromAuthService.refreshToken, keycloakToken.refreshToken)
        assertEquals(keycloakTokenFromAuthService.tokenType, keycloakToken.tokenType)
        verify(driverSubject).notifyDriverObservers(driverDetails)
    }

    @Test
    fun saveAdminInKeycloakTest_ReturnsKeycloakToken() {
        //given
        val adminDetails = AdminDetails("username", "email@gmail.com",
            "+375293333333", "password")
        val keycloakToken = KeycloakToken("a.b.c", 1, 10, "d.e.f",
            "Bearer")
        `when`(keycloakService.saveUserInKeycloak(adminDetails.username, adminDetails.email,
            adminDetails.password, "ROLE_ADMIN", true)).thenReturn(keycloakToken)

        //when
        val keycloakTokenFromAuthService = authService.saveAdmin(adminDetails)

        //then
        assertNotNull(keycloakTokenFromAuthService)
        assertEquals(keycloakTokenFromAuthService.accessToken, keycloakToken.accessToken)
        assertEquals(keycloakTokenFromAuthService.expiresIn, keycloakToken.expiresIn)
        assertEquals(keycloakTokenFromAuthService.refreshExpiresIn, keycloakToken.refreshExpiresIn)
        assertEquals(keycloakTokenFromAuthService.refreshToken, keycloakToken.refreshToken)
        assertEquals(keycloakTokenFromAuthService.tokenType, keycloakToken.tokenType)
    }

    @Test
    fun passAuthorizationTest_ReturnsKeycloakToken() {
        //given
        val userDetails = UserAuthDetails("username", "password")
        val keycloakToken = KeycloakToken("a.b.c", 1, 10, "d.e.f",
            "Bearer")
        `when`(keycloakService.getAccessAndRefreshTokens(userDetails.username, userDetails.password))
            .thenReturn(keycloakToken)

        //when
        val keycloakTokenFromAuthService = authService.passAuthorization(userDetails)

        //then
        assertNotNull(keycloakTokenFromAuthService)
        assertEquals(keycloakTokenFromAuthService.accessToken, keycloakToken.accessToken)
        assertEquals(keycloakTokenFromAuthService.expiresIn, keycloakToken.expiresIn)
        assertEquals(keycloakTokenFromAuthService.refreshExpiresIn, keycloakToken.refreshExpiresIn)
        assertEquals(keycloakTokenFromAuthService.refreshToken, keycloakToken.refreshToken)
    }

    @Test
    fun resendVerificationCodeTest_ReturnsUnit() {
        //given
        val email = "username@gmail.com"

        //when
        authService.resendVerificationCode(email)

        //then
        verify(mailSenderService).sendVerificationCode(email)
    }

    @Test
    fun verifyEmailTest_ReturnsUnit() {
        //given
        val email = "username@gmail.com"
        val verificationCode = "333333"

        //when
        authService.verifyEmail(email, verificationCode)

        //then
        verify(mailSenderService).isCodeVerificationPassed(verificationCode)
        verify(keycloakService).confirmEmail(email)
    }
}