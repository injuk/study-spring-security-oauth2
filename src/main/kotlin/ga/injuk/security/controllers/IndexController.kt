package ga.injuk.security.controllers

import ga.injuk.security.models.User
import ga.injuk.security.repositories.UserRepository
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class IndexController(
    private val encoder: BCryptPasswordEncoder,
    private val userRepository: UserRepository,
) {

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