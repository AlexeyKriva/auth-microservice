package com.software.modsen.authmicroservice.exceptions

class UserNotFoundException(
    customMessage: String
): RuntimeException(customMessage)