package services

import com.software.modsen.authmicroservice.entities.mail.VerificationCode
import com.software.modsen.authmicroservice.services.MailSenderService
import com.software.modsen.authmicroservice.services.VerificationCodeService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class MailSenderServiceTests {
    @Mock
    lateinit var javaMailSender: JavaMailSender

    @Mock
    lateinit var verificationCodeService: VerificationCodeService

    @InjectMocks
    lateinit var mailSenderService: MailSenderService

    @Test
    fun sendVerificationCodeTest_ReturnsVerificationCode() {
        //given
        val verificationCode = VerificationCode("123456", LocalDateTime.now())

        `when`(verificationCodeService.createRandomVerificationCode()).thenReturn(verificationCode)
        doNothing().`when`(javaMailSender).send(any(SimpleMailMessage::class.java))

        //when
        val verificationCodeFromMailSender = mailSenderService.sendVerificationCode("alex@gmail.com")

        //then
        assertNotNull(verificationCodeFromMailSender)
        assertEquals(verificationCodeFromMailSender.code, verificationCode.code)
        verify(javaMailSender).send(any(SimpleMailMessage::class.java))
    }

    @Test
    fun isCodeVerificationPassedTest_ReturnsTrue() {
        //given
        val userVerificationCode = "123456"
        `when`(verificationCodeService.isVerificationCodeValid(userVerificationCode)).thenReturn(true)

        //when
        val isCodeVerificationPassed = mailSenderService.isCodeVerificationPassed(userVerificationCode)

        //then
        verify(verificationCodeService).isVerificationCodeValid(userVerificationCode)
        assertTrue(isCodeVerificationPassed)
    }
}