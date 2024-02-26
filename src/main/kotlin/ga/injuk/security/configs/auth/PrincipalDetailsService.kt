package ga.injuk.security.configs.auth

import ga.injuk.security.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class PrincipalDetailsService(
    private val userRepository: UserRepository,
): UserDetailsService {

    /**
     * 스프링 시큐리티 설정 상 loginProcessingUrl("/login")으로 인해 /login에 대한 요청이 발생한 경우,
     * 자동으로 UserDetailsService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행된다.
     * 반환된 UserDetails는 Authentication 내부에 포함된 후, 스프링 시큐리티의 세션 영역에 저장된다.
     * 이러한 과정은 자동으로 실행되므로, Authentication 객체를 생성하거나 이를 세션 영역에 저장하는 과정은 직접 작성할 필요가 없다!
     */
    override fun loadUserByUsername(username: String?): UserDetails? {
        require(username != null) { "username cannotbe null" }

        val user = userRepository.findByUsername(username)

        return user?.let { PrincipalDetails(user = it, attributes = mutableMapOf()) }
    }
}