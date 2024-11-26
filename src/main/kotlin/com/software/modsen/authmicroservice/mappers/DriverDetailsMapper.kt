package com.software.modsen.authmicroservice.mappers

import com.software.modsen.authmicroservice.entities.auth.driver.DriverDetails
import com.software.modsen.authmicroservice.entities.auth.driver.DriverDetailsDto

class DriverDetailsMapper {
    fun fromDriverDetailsToDriverDetailsDto(driverDetails: DriverDetails): DriverDetailsDto {
        return DriverDetailsDto(driverDetails.username, driverDetails.email, driverDetails.phoneNumber,
            driverDetails.sex, driverDetails.carId)
    }
}