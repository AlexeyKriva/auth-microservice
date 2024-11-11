package com.software.modsen.authmicroservice.observer.passenger

import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PassengerSubject {
    private var passengerObservers: MutableList<PassengerObserver> = mutableListOf()

    fun addPassengerObserver(passengerObserver: PassengerObserver) {
        passengerObservers.add(passengerObserver)
    }

    fun notifyPassengerObservers(passengerDetails: PassengerDetails) {
        for (passengerObserver in passengerObservers) {
            if (passengerObserver is PassengerMailObserver) {
                CoroutineScope(Dispatchers.Default).launch {
                    passengerObserver.passengerRegistration(passengerDetails)
                }
            } else {
                passengerObserver.passengerRegistration(passengerDetails)
            }
        }
    }
}