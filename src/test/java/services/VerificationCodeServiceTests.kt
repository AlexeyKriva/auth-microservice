package services

import com.software.modsen.authmicroservice.entities.mail.VerificationCode
import com.software.modsen.authmicroservice.services.VerificationCodeService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class VerificationCodeServiceTests {
    @InjectMocks
    lateinit var verificationCodeService: VerificationCodeService

    @Test
    fun createRandomVerificationCodeTest_ReturnsVerificationCode() {
        //when
        val verificationCode = verificationCodeService.createRandomVerificationCode()

        //then
        assertNotNull(verificationCode)
        assertEquals(verificationCode.code.length, 6)
        assertTrue(verificationCode.createdAt.isBefore(LocalDateTime.now()))
        assertEquals(verificationCode.expirationTimeInMinutes, 5)
    }

    @Test
    fun isVerificationCodeValidTest_ReturnsTrue() {
        //given
        val userVerificationCode = "123456"
        val verificationCode = VerificationCode(userVerificationCode, LocalDateTime.now(), 5)
        verificationCodeService.setVerificationCode(verificationCode)

        //when
        val isVerificationCodeValid = verificationCodeService.isVerificationCodeValid(userVerificationCode)

        //then
        assertTrue(isVerificationCodeValid)
    }

    @Test
    fun isVerificationCodeNotExpired_ReturnsTrue() {
        //given
        val verificationCode = VerificationCode("123456", LocalDateTime.now(), 5)
        verificationCodeService.setVerificationCode(verificationCode)

        //when
        val isVerificationCodeNotExpired = verificationCodeService.isVerificationCodeNotExpired(verificationCode)

        //then
        assertTrue(isVerificationCodeNotExpired)
    }
}