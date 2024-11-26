package com.software.modsen.authmicroservice.observer.passenger

import com.software.modsen.authmicroservice.clients.PassengerClient
import com.software.modsen.authmicroservice.services.KeycloakService
import com.software.modsen.authmicroservice.services.MailSenderService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PassengerObserverConfig {
    @Bean
    fun passengerSubject(
        passengerClient: PassengerClient,
        mailSenderService: MailSenderService
    ): PassengerSubject {
        val passengerSubject = PassengerSubject()
        passengerSubject.addPassengerObserver(
            PassengerMicroserviceObserver(
                passengerClient
            )
        )
        passengerSubject.addPassengerObserver(
            PassengerMailObserver(
                mailSenderService
            )
        )

        return passengerSubject
    }
}