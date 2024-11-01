package com.software.modsen.authmicroservice.clients

import com.software.modsen.authmicroservice.entities.auth.driver.DriverDetailsDto
import com.software.modsen.authmicroservice.entities.auth.driver.DriverDetailsFromService
import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetailsDto
import com.software.modsen.authmicroservice.entities.auth.passenger.PassengerDetailsFromService
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "driver-microservice")
interface DriverClient {
    @PostMapping
    fun saveDriver(
        @RequestBody driverDetailsDto: DriverDetailsDto
    ): ResponseEntity<DriverDetailsFromService>;
}