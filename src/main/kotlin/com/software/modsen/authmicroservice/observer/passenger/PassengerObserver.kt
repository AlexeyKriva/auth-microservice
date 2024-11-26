package com.software.modsen.authmicroservice.observer.passenger

import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetails

interface PassengerObserver {
    fun passengerRegistration(passengerDetails: PassengerDetails)
}