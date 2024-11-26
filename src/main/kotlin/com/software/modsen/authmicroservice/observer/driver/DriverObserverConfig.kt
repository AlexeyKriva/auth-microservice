package com.software.modsen.authmicroservice.observer.driver

import com.software.modsen.authmicroservice.clients.DriverClient
import com.software.modsen.authmicroservice.services.MailSenderService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DriverObserverConfig {
    @Bean
    fun driverSubject(
        driverClient: DriverClient,
        mailSenderService: MailSenderService
    ): DriverSubject {
        val driverSubject = DriverSubject()
        driverSubject.addDriverObserver(
            DriverMicroserviceObserver(
                driverClient
            )
        )
        driverSubject.addDriverObserver(
            DriverMailObserver(
                mailSenderService
            )
        )

        return driverSubject
    }
}