package ga.injuk.security.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // 1. secured 어노테이션을 활성화한다! 2. preAuthorize와 postAuthorize 어노테이션을 활성화한다!
class SecurityConfig {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http.run {
        csrf { it.disable() }
        authorizeHttpRequests {
            it
                // 로그인 사용자만 허용
                .requestMatchers("/user/**").authenticated()
                // 로그인 사용자 + admin + manager 권한만 허용
                .requestMatchers("/manager/**").access(
                    WebExpressionAuthorizationManager("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                )
                // 로그인 사용자 + admin 권한만 허용
                .requestMatchers("/admin/**").access(
                    WebExpressionAuthorizationManager("hasRole('ROLE_ADMIN')")
                )
                // 그 외 모든 요청은 허용
                .anyRequest().permitAll()
        }

        // 로그인 페이지, 인증이 필요한 경우 무조건 아래의 로그인 폼을 사용한다.
        formLogin {
            it
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // /login 호출 시 스프링 시큐리티가 이를 가로채어 로그인을 진행
                .defaultSuccessUrl("/") // 로그인 성공시 리다이렉트
        }

        oauth2Login {
            // 일반 로그인 페이지와 oauth2.0 용 로그인 페이지의 주소를 맞춰주었다.
            it.loginPage("/loginForm") // 구글 로그인 완료된 후의 후처리가 필요할 것!
        }

        build()
    }
}