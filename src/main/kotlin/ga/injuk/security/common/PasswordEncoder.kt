package ga.injuk.security.common

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

// 요렇게 하면 안된다! 로그인 과정에서도 스프링 시큐리티가 패스워드 인코더를 가져가 검증에 활용하기 때문인 듯!
object PasswordEncoder {
    private val encoder = BCryptPasswordEncoder()

    fun encode(plainText: String): String = encoder.encode(plainText)
}