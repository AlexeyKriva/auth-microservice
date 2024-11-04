package com.software.modsen.authmicroservice.exceptions

class ExceptionMessage {
    companion object {
        const val USER_IS_ALREADY_REGISTERED_MESSAGE = "User with the same credentials is already registered."
        const val METHOD_NOT_SUPPORTED_MESSAGE: String = " method is not supported."
        const val INVALID_JSON_FORMAT: String = "Invalid json format."
        const val FAILED_REQUEST_TO_REMOTE_SERVER_MESSAGE = "Failed request to remote server. "
        const val ROLE_NOT_FOUND_MESSAGE = " no found. "
        const val VERIFICATION_CODE_HAS_EXPIRED_MESSAGE =
            "Verification code has expired. Please request a new verification code."
        const val WRONG_VERIFICATION_CODE_MESSAGE = "Wrong verification code"
    }
}