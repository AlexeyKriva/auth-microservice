package com.software.modsen.authmicroservice.services

import com.software.modsen.authmicroservice.entities.auth.admin.AdminDetails
import com.software.modsen.authmicroservice.entities.auth.driver.DriverDetails
import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetails
import com.software.modsen.authmicroservice.entities.keycloak.KeycloakToken
import com.software.modsen.authmicroservice.entities.keycloak.UserAuthDetails
import com.software.modsen.authmicroservice.observer.driver.DriverSubject
import com.software.modsen.authmicroservice.observer.passenger.PassengerSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val keycloakService: KeycloakService,
    private val passengerSubject: PassengerSubject,
    private val driverSubject: DriverSubject,
    private val mailSenderService: MailSenderService
) {

    fun savePassenger(passengerDetails: PassengerDetails): KeycloakToken {
        val keycloakToken = keycloakService.saveUserInKeycloak(
            passengerDetails.username,
            passengerDetails.email, passengerDetails.password, "ROLE_PASSENGER", false
        )

        passengerSubject.notifyPassengerObservers(passengerDetails)

        return keycloakToken
    }

    fun saveDriver(driverDetails: DriverDetails): KeycloakToken {
        val keycloakToken = keycloakService.saveUserInKeycloak(
            driverDetails.username,
            driverDetails.email, driverDetails.password, "ROLE_DRIVER", false
        )

        driverSubject.notifyDriverObservers(driverDetails)

        return keycloakToken
    }

    fun saveAdmin(adminDetails: AdminDetails): KeycloakToken {
        val keycloakToken = keycloakService.saveUserInKeycloak(
            adminDetails.username,
            adminDetails.email, adminDetails.password, "ROLE_ADMIN", true
        )

        return keycloakToken
    }

    fun passAuthorization(userAuthDetails: UserAuthDetails): KeycloakToken {
        return keycloakService.getAccessAndRefreshTokens(userAuthDetails.username, userAuthDetails.password)
    }

    fun resendVerificationCode(email: String): Unit {
        mailSenderService.sendVerificationCode(email)
    }

    fun verifyEmail(userEmail: String, userVerificationCode: String): Unit {
        mailSenderService.isCodeVerificationPassed(userVerificationCode)
        keycloakService.confirmEmail(userEmail)
    }
}