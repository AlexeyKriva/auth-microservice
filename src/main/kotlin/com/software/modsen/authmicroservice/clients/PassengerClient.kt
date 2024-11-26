package com.software.modsen.authmicroservice.clients

import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetailsFromService
import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetailsDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "passenger-microservice")
interface PassengerClient {
    @PostMapping
    fun savePassenger(
        @RequestBody passengerDetailsDto: PassengerDetailsDto
    ): ResponseEntity<PassengerDetailsFromService>;
}