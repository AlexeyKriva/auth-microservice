package com.software.modsen.authmicroservice.mappers

import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetails
import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetailsDto

class PassengerDetailsMapper {
    fun fromPassengerDetailsToPassengerDetailsDto(passengerDetails: PassengerDetails): PassengerDetailsDto {
        return PassengerDetailsDto(passengerDetails.username, passengerDetails.email,
            passengerDetails.phoneNumber)
    }
}