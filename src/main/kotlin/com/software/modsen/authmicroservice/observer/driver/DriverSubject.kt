package com.software.modsen.authmicroservice.observer.driver

import com.software.modsen.authmicroservice.entities.auth.driver.DriverDetails
import com.software.modsen.authmicroservice.observer.passenger.PassengerMailObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DriverSubject {
    private var driverObservers: MutableList<DriverObserver> = mutableListOf()

    fun addDriverObserver(driverObserver: DriverObserver) {
        driverObservers.add(driverObserver)
    }

    fun notifyDriverObservers(driverDetails: DriverDetails) {
        for (driverObserver in driverObservers) {
            if (driverObserver is DriverMailObserver) {
                CoroutineScope(Dispatchers.Default).launch {
                    driverObserver.driverRegistration(driverDetails)
                }
            } else {
                driverObserver.driverRegistration(driverDetails)
            }
        }
    }
}