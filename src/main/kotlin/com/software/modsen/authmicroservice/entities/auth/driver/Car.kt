package com.software.modsen.authmicroservice.entities.auth.driver

data class Car (
    private val id: Long,

    private val color: CarColor,

    private val brand: CarBrand,

    private val carNumber: String,

    private val isDeleted: Boolean
)