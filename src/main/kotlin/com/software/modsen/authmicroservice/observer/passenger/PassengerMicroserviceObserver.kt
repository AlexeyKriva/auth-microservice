package com.software.modsen.authmicroservice.observer.passenger

import com.software.modsen.authmicroservice.clients.PassengerClient
import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetails
import com.software.modsen.authmicroservice.mappers.PassengerDetailsMapper

class PassengerMicroserviceObserver(private val passengerClient: PassengerClient): PassengerObserver {
    override fun passengerRegistration(passengerDetails: PassengerDetails) {
        val passengerDetailsMapper = PassengerDetailsMapper()

        passengerClient.savePassenger(passengerDetailsMapper
            .fromPassengerDetailsToPassengerDetailsDto(passengerDetails))
    }
}