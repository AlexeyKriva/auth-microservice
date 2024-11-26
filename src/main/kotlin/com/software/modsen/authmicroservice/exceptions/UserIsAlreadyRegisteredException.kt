package com.software.modsen.authmicroservice.exceptions

class UserIsAlreadyRegisteredException(
    customMessage: String
): RuntimeException(customMessage)