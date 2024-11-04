package com.software.modsen.authmicroservice.observer.passenger

import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetails

class PassengerSubject {
    private var passengerObservers: MutableList<PassengerObserver> = mutableListOf()

    fun addPassengerObserver(passengerObserver: PassengerObserver): Unit {
        passengerObservers.add(passengerObserver)
    }

    fun notifyPassengerObservers(passengerDetails: PassengerDetails): Unit {
        for (passengerObserver in passengerObservers) {
            passengerObserver.passengerRegistration(passengerDetails)
        }
    }
}