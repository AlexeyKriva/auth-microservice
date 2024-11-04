package com.software.modsen.authmicroservice.services

import com.software.modsen.authmicroservice.entities.mail.VerificationCode
import com.software.modsen.authmicroservice.exceptions.EmailVerificationCodeHasExpiredException
import com.software.modsen.authmicroservice.exceptions.WrongEmailVerificationCodeException
import com.software.modsen.authmicroservice.exceptions.ExceptionMessage.Companion.VERIFICATION_CODE_HAS_EXPIRED_MESSAGE
import com.software.modsen.authmicroservice.exceptions.ExceptionMessage.Companion.WRONG_VERIFICATION_CODE_MESSAGE
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class VerificationCodeService {
    private val availableSymbols: List<String> = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")

    private var verificationCode: VerificationCode = createRandomVerificationCode()

    fun setVerificationCode(code: VerificationCode) {
        verificationCode = code
    }

    fun getVerificationCode(): VerificationCode {
        return verificationCode
    }

    fun createRandomVerificationCode(): VerificationCode {
        val length = 6;

        val code = (1..length)
            .map {availableSymbols.random()}
            .joinToString("")

        verificationCode = VerificationCode(code, LocalDateTime.now())

        return verificationCode as VerificationCode
    }

    fun isVerificationCodeValid(userVerificationCode: String): Boolean {
        if (isVerificationCodeNotExpired(verificationCode)) {
            if (verificationCode.code.equals(userVerificationCode)) {
                return true
            }

            throw WrongEmailVerificationCodeException(WRONG_VERIFICATION_CODE_MESSAGE)
        }

        throw EmailVerificationCodeHasExpiredException(VERIFICATION_CODE_HAS_EXPIRED_MESSAGE)
    }

    fun isVerificationCodeNotExpired(verificationCode: VerificationCode): Boolean {
        val duration = Duration.between(LocalDateTime.now(), verificationCode.createdAt).toMinutes()

        return duration < verificationCode.expirationTimeInMinutes
    }
}