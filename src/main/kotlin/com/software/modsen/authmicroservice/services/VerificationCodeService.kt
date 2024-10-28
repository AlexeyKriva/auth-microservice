package com.software.modsen.authmicroservice.services

import com.software.modsen.authmicroservice.entities.VerificationCode
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class VerificationCodeService {
    private val availableSymbols: List<String> = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")

    private var verificationCode: VerificationCode? = null

    public fun createVerificationCode(): VerificationCode {
        val length = 6;

        val code = (1..length)
            .map {availableSymbols.random()}
            .joinToString("")

        verificationCode = VerificationCode(code, LocalDateTime.now())

        return verificationCode as VerificationCode
    }

    public fun isVerificationCodeValid(verificationCode: VerificationCode, userCode: String): Boolean {
        return isVerificationCodeNotExpired(verificationCode) && verificationCode.code.equals(userCode)
    }

    public fun isVerificationCodeNotExpired(verificationCode: VerificationCode): Boolean {
        val duration = Duration.between(LocalDateTime.now(), verificationCode.createdAt).toMinutes()
        return duration < verificationCode.expirationTimeInMinutes
    }
}