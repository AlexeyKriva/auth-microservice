package com.software.modsen.authmicroservice.observer.driver

import com.software.modsen.authmicroservice.entities.auth.driver.DriverDetails
import com.software.modsen.authmicroservice.services.MailSenderService

class DriverMailObserver(private val mailSenderService: MailSenderService): DriverObserver {
    override fun driverRegistration(driverDetails: DriverDetails) {
        mailSenderService.sendVerificationCode(driverDetails.email)
    }
}