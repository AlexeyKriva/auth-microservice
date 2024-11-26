package com.software.modsen.authmicroservice.services

import com.software.modsen.authmicroservice.entities.mail.VerificationCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailSenderService(private val verificationCodeService: VerificationCodeService,
                        private val mailSender: JavaMailSender) {
    @Value("\${spring.mail.username}")
    private val from: String? = null;

    val subject: String = "Account verification";

    fun sendVerificationCode(to: String): VerificationCode {
        val verificationCode: VerificationCode = verificationCodeService.createRandomVerificationCode()

        val mailVerificationMessage = SimpleMailMessage()

        mailVerificationMessage.setTo(to)

        mailVerificationMessage.apply {
            this.subject = subject
            this.text = verificationCode.code
            this.from = from
        }

        mailSender.send(mailVerificationMessage)

        return verificationCode
    }

    fun isCodeVerificationPassed(userVerificationCode: String): Boolean {
        return verificationCodeService.isVerificationCodeValid(userVerificationCode)
    }
}