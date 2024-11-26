package com.software.modsen.authmicroservice.observer.passenger

import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetails
import com.software.modsen.authmicroservice.services.MailSenderService

class PassengerMailObserver(private val mailSenderService: MailSenderService) : PassengerObserver {
    override fun passengerRegistration(passengerDetails: PassengerDetails) {
        mailSenderService.sendVerificationCode(passengerDetails.email)
    }
}