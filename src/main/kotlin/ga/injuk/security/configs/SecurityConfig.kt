package ga.injuk.security.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
class SecurityConfig {
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


//        logout {
//            it.log
//        }

        // 로그인 페이지
        formLogin { it.loginPage("/login") }

        build()
    }
}