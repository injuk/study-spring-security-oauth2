package ga.injuk.security.common

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

object PasswordEncoder {
    private val encoder = BCryptPasswordEncoder()

    fun encode(plainText: String): String = encoder.encode(plainText)
}