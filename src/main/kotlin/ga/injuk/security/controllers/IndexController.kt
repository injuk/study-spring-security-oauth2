package ga.injuk.security.controllers

import ga.injuk.security.configs.auth.PrincipalDetails
import ga.injuk.security.models.User
import ga.injuk.security.repositories.UserRepository
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class IndexController(
    private val encoder: BCryptPasswordEncoder,
    private val userRepository: UserRepository,
) {

    // 아래 메소드 시그니쳐에 명시된 두 인자로부터 각각 동일한 결과를 얻을 수 있다.
    @GetMapping("/test/login")
    @ResponseBody
    fun testLogin(auth: Authentication, @AuthenticationPrincipal userDetails: PrincipalDetails): String { // Authentication 객체를 DI 받는다.
        println("/test/login =======")
        val details = auth.principal as PrincipalDetails
        println("auth: ${details.user}")

        println("userDetails: ${userDetails.user}")

        return "세션 정보 확인!"
    }

    @GetMapping("/test/oauth/login")
    @ResponseBody
    fun testOauthLogin(auth: Authentication, @AuthenticationPrincipal oAuth2User: OAuth2User): String { // Authentication 객체를 DI 받는다.
        println("/test/oauth/login =======")
        val details = auth.principal as OAuth2User
        println("auth: ${details.attributes}") // 요 정보는 PrincipalOauth2UserSevice에서 받은 정보와 같다!

        println("oauth2User: ${oAuth2User.attributes}")
        return "Oauth 세션 정보 확인!"
    }

    @GetMapping(path = ["", "/"])
    fun index(): String = "index"

    @GetMapping("/user")
    @ResponseBody
    fun user(): String {
        return "user"
    }

    @GetMapping("/admin")
    @ResponseBody
    fun admin(): String {
        return "admin"
    }

    @GetMapping("/manager")
    @ResponseBody
    fun manager(): String {
        return "manager"
    }

    @GetMapping("/loginForm")
    fun loginForm(): String {
        return "loginForm"
    }

    @GetMapping("/joinForm")
    fun joinForm(): String {
        return "joinForm"
    }

    @PostMapping("/join")
    fun join(user: User): String {
        userRepository.save(user.copy(
            password = encoder.encode(user.password)
        ))

        return "redirect:/loginForm"
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    @ResponseBody
    fun info(): String {
        return "개인정보"
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    @ResponseBody
    fun data(): String {
        return "데이터 정보"
    }
}