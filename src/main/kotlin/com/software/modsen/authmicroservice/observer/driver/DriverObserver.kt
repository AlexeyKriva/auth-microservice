package com.software.modsen.authmicroservice.observer.driver

import com.software.modsen.authmicroservice.entities.auth.driver.DriverDetails
import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetails

interface DriverObserver {
    fun driverRegistration(driverDetails: DriverDetails)
}