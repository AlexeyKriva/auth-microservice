package com.software.modsen.authmicroservice.observer.driver

import com.software.modsen.authmicroservice.entities.auth.driver.DriverDetails

class DriverSubject {
    private var driverObservers: MutableList<DriverObserver> = mutableListOf()

    fun addDriverObserver(driverObserver: DriverObserver): Unit {
        driverObservers.add(driverObserver)
    }

    fun notifyDriverObservers(driverDetails: DriverDetails): Unit {
        for (driverObserver in driverObservers) {
            driverObserver.driverRegistration(driverDetails)
        }
    }
}