package com.software.modsen.authmicroservice.observer.driver

import com.software.modsen.authmicroservice.clients.DriverClient
import com.software.modsen.authmicroservice.clients.PassengerClient
import com.software.modsen.authmicroservice.entities.auth.driver.DriverDetails
import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetails
import com.software.modsen.authmicroservice.mappers.DriverDetailsMapper

class DriverMicroserviceObserver(private val driverClient: DriverClient): DriverObserver {
    override fun driverRegistration(driverDetails: DriverDetails) {
        val driverDetailsMapper = DriverDetailsMapper()

        driverClient.saveDriver(driverDetailsMapper
            .fromDriverDetailsToDriverDetailsDto(driverDetails))
    }
}