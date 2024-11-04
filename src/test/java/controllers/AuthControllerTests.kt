package controllers

import com.software.modsen.authmicroservice.controllers.AuthController
import com.software.modsen.authmicroservice.entities.auth.admin.AdminDetails
import com.software.modsen.authmicroservice.entities.auth.driver.DriverDetails
import com.software.modsen.authmicroservice.entities.auth.driver.Sex
import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetails
import com.software.modsen.authmicroservice.entities.keycloak.KeycloakToken
import com.software.modsen.authmicroservice.entities.keycloak.UserAuthDetails
import com.software.modsen.authmicroservice.entities.mail.EmailConfirmation
import com.software.modsen.authmicroservice.entities.mail.EmailForVerification
import com.software.modsen.authmicroservice.services.AuthService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class AuthControllerTests {
    @InjectMocks
    lateinit var authController: AuthController

    @Mock
    lateinit var authService: AuthService

    @Test
    fun registrationPassengerTest_ReturnsResponseEntity() {
        //given
        val passengerDetails = PassengerDetails("username", "email@gmail.com",
            "+375293333333", "password")
        val keycloakToken = KeycloakToken("a.b.c", 1, 10, "d.e.f",
            "Bearer")

        `when`(authService.savePassenger(passengerDetails)).thenReturn(keycloakToken)

        //when
        val responseEntity = authController.registrationPassenger(passengerDetails)

        //then
        assertNotNull(responseEntity)
        assertEquals(responseEntity.body!!.accessToken, keycloakToken.accessToken)
        assertEquals(responseEntity.body!!.expiresIn, keycloakToken.expiresIn)
        assertEquals(responseEntity.body!!.refreshExpiresIn, keycloakToken.refreshExpiresIn)
        assertEquals(responseEntity.body!!.refreshToken, keycloakToken.refreshToken)
        assertEquals(responseEntity.body!!.tokenType, keycloakToken.tokenType)
        verify(authService).savePassenger(passengerDetails)
    }

    @Test
    fun registrationDriverTest_ReturnsResponseEntity() {
        //given
        val driverDetails = DriverDetails("username", "email@gmail.com",
            "+375293333333", "password", Sex.MALE, 1)
        val keycloakToken = KeycloakToken("a.b.c", 1, 10, "d.e.f",
            "Bearer")

        `when`(authService.saveDriver(driverDetails)).thenReturn(keycloakToken)

        //when
        val responseEntity = authController.registrationDriver(driverDetails)

        //then
        assertNotNull(responseEntity)
        assertEquals(responseEntity.body!!.accessToken, keycloakToken.accessToken)
        assertEquals(responseEntity.body!!.expiresIn, keycloakToken.expiresIn)
        assertEquals(responseEntity.body!!.refreshExpiresIn, keycloakToken.refreshExpiresIn)
        assertEquals(responseEntity.body!!.refreshToken, keycloakToken.refreshToken)
        assertEquals(responseEntity.body!!.tokenType, keycloakToken.tokenType)
        verify(authService).saveDriver(driverDetails)
    }

    @Test
    fun registrationAdminTest_ReturnsResponseEntity() {
        //given
        val adminDetails = AdminDetails("username", "email@gmail.com",
            "+375293333333", "password")
        val keycloakToken = KeycloakToken("a.b.c", 1, 10, "d.e.f",
            "Bearer")

        `when`(authService.saveAdmin(adminDetails)).thenReturn(keycloakToken)

        //when
        val responseEntity = authController.registrationAdmin(adminDetails)

        //then
        assertNotNull(responseEntity)
        assertEquals(responseEntity.body!!.accessToken, keycloakToken.accessToken)
        assertEquals(responseEntity.body!!.expiresIn, keycloakToken.expiresIn)
        assertEquals(responseEntity.body!!.refreshExpiresIn, keycloakToken.refreshExpiresIn)
        assertEquals(responseEntity.body!!.refreshToken, keycloakToken.refreshToken)
        assertEquals(responseEntity.body!!.tokenType, keycloakToken.tokenType)
        verify(authService).saveAdmin(adminDetails)
    }

    @Test
    fun authUserTest_ReturnsResponseEntity() {
        //given
        val userAuthDetails = UserAuthDetails("username", "password")
        val keycloakToken = KeycloakToken("a.b.c", 1, 10, "d.e.f",
            "Bearer")

        `when`(authService.passAuthorization(userAuthDetails)).thenReturn(keycloakToken)

        //when
        val responseEntity = authController.authUser(userAuthDetails)

        //then
        assertNotNull(responseEntity)
        assertEquals(responseEntity.body!!.accessToken, keycloakToken.accessToken)
        assertEquals(responseEntity.body!!.expiresIn, keycloakToken.expiresIn)
        assertEquals(responseEntity.body!!.refreshExpiresIn, keycloakToken.refreshExpiresIn)
        assertEquals(responseEntity.body!!.refreshToken, keycloakToken.refreshToken)
        assertEquals(responseEntity.body!!.tokenType, keycloakToken.tokenType)
        verify(authService).passAuthorization(userAuthDetails)
    }

    @Test
    fun resendVerificationCodeTest_ReturnsResponseEntity() {
        //given
        val emailForVerification = EmailForVerification("username@gmail.com")

        //when
        val responseEntity = authController.resendVerificationCode(emailForVerification)

        //then
        assertNotNull(responseEntity)
        verify(authService).resendVerificationCode(emailForVerification.email)
    }

    @Test
    fun verifyEmailTest_ReturnsResponseEntity() {
        //given
        val emailConfirmation = EmailConfirmation("username@gmail.com", "123456")

        //when
        val responseEntity = authController.verifyEmail(emailConfirmation)

        //then
        assertNotNull(responseEntity)
        verify(authService).verifyEmail(emailConfirmation.emailForVerification, emailConfirmation.userVerificationCode)
    }
}