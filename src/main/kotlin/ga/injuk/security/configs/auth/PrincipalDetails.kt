package ga.injuk.security.configs.auth

import ga.injuk.security.models.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

/**
 * 스프링 시큐리티가 /login에 대한 접근을 가로채어 로그인을 진행시킬 때,
 * 로그인 진행 완료시 스프링 시큐리티 session을 만들어주기 위함
 * 스프링 시큐리티는 자신만의 세션 공간을 가지며, SecurityContextHolder에 키 값을 기반으로 세션을 저장한다.
 * 이 때, 스프링 시큐리티의 세션 저장소에는 Authentication 객체만을 저장할 수 있다.
 * 또한, Authentication 객체는 사용자 정보를 가지며 이는 UserDetails 타입 객체가 되어야 한다.
 * 다시 말해, PrincipalDetails는 User 정보를 품고 Authentication 객체에 포함되어 스프링 시큐리티 세션 영역에 저장되어야 한다.
 */
class PrincipalDetails(
    val user: User,
    private val attributes: MutableMap<String, Any>,
) : UserDetails, OAuth2User {
    // 해당 User의 권한을 반환
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(object : GrantedAuthority {
            override fun getAuthority(): String = user.role
        })
    }

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    // 예를 들어 정책 상 마지막 로그인 날짜로부터 1년이 지난 경우 휴면이라면, 이를 계산하여 false를 반환할 수 있다.
    override fun isEnabled(): Boolean = true

    override fun getName(): String? {
        return attributes["sub"] as? String
    }

    override fun <A : Any?> getAttribute(name: String?): A? {
        return super.getAttribute(name)
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return attributes
    }
}