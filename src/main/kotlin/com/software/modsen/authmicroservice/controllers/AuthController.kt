package com.software.modsen.authmicroservice.controllers

import com.software.modsen.authmicroservice.entities.auth.admin.AdminDetails
import com.software.modsen.authmicroservice.entities.auth.driver.DriverDetails
import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetails
import com.software.modsen.authmicroservice.entities.keycloak.KeycloakToken
import com.software.modsen.authmicroservice.entities.keycloak.UserAuthDetails
import com.software.modsen.authmicroservice.entities.mail.EmailConfirmation
import com.software.modsen.authmicroservice.entities.mail.EmailForVerification
import com.software.modsen.authmicroservice.services.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/auth"], produces = ["application/json"])
class AuthController(private val authService: AuthService) {
    @PostMapping("/passengers/register")
    fun registrationPassenger(
        @Valid @RequestBody passengerDetails: PassengerDetails
    ): ResponseEntity<KeycloakToken> {
        return ResponseEntity(authService.savePassenger(passengerDetails), HttpStatus.CREATED)
    }

    @PostMapping("/drivers/register")
    fun registrationDriver(
        @Valid @RequestBody driverDetails: DriverDetails
    ): ResponseEntity<KeycloakToken> {
        return ResponseEntity(authService.saveDriver(driverDetails), HttpStatus.CREATED)
    }

    @PostMapping("/admins/register")
    fun registrationAdmin(
        @Valid @RequestBody adminDetails: AdminDetails
    ): ResponseEntity<KeycloakToken> {
        return ResponseEntity(authService.saveAdmin(adminDetails), HttpStatus.CREATED)
    }

    @PostMapping("/users")
    fun authUser(
        @Valid @RequestBody userAuthDetails: UserAuthDetails
    ): ResponseEntity<KeycloakToken> {
        return ResponseEntity(authService.passAuthorization(userAuthDetails), HttpStatus.OK)
    }

    @PostMapping("/email/verify/resend")
    fun resendVerificationCode(
        @Valid @RequestBody emailForVerification: EmailForVerification
    ): ResponseEntity<Unit> {
        authService.resendVerificationCode(emailForVerification.email)

        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping("/email/verify")
    fun verifyEmail(
        @Valid @RequestBody emailConfirmation: EmailConfirmation
    ): ResponseEntity<Unit> {
        authService.verifyEmail(emailConfirmation.emailForVerification,
            emailConfirmation.userVerificationCode)

        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping("/passwords")
    fun changePassword(
        @Valid @RequestBody userAuthDetails: UserAuthDetails
    ): ResponseEntity<Unit> {
        return ResponseEntity(authService.changePassword(userAuthDetails), HttpStatus.OK)
    }
}