package com.software.modsen.authmicroservice.exceptions

class ExceptionMessage {
    val USER_IS_ALREADY_REGISTERED_MESSAGE = "User with the same credentials is already registered."
    val METHOD_NOT_SUPPORTED_MESSAGE: String = " method is not supported."
    val INVALID_JSON_FORMAT: String = "Invalid json format."
    val FAILED_REQUEST_TO_REMOTE_SERVER_MESSAGE = "Failed request to remote server. "
    val ROLE_NOT_FOUND_MESSAGE = " no found. "
    val VERIFICATION_CODE_HAS_EXPIRED_MESSAGE =
        "Verification code has expired. Please request a new verification code."
    val WRONG_VERIFICATION_CODE_MESSAGE = "Wrong verification code"
    val USER_NOT_FOUND = "{\"error\": \"user not found.\"}"
}