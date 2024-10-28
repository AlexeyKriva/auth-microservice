package com.software.modsen.authmicroservice.services

import com.software.modsen.authmicroservice.entities.VerificationCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class MailSenderService(private val verificationCodeService: VerificationCodeService,
                        private val mailSender: JavaMailSender) {
    @Value("\${spring.mail.username}")
    private val from: String? = null;

    val subject: String = "Account verification";

    public fun sendVerificationCode(to: String): VerificationCode {
        val verificationCode: VerificationCode = verificationCodeService.createVerificationCode()

        var mailVerificationMessage: SimpleMailMessage = SimpleMailMessage()

        mailVerificationMessage.setTo(to)
        mailVerificationMessage.subject = subject
        mailVerificationMessage.text = verificationCode.code
        mailVerificationMessage.from = from

        mailSender.send(mailVerificationMessage)

        return verificationCode
    }

    public fun isCodeVerificationPassed(verificationCode: VerificationCode, userVerificationCode: String): Boolean {
        return verificationCodeService.isVerificationCodeValid(verificationCode, userVerificationCode)
    }
}