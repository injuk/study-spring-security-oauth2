package ga.injuk.security.controllers

import ga.injuk.security.common.Constants
import ga.injuk.security.models.User
import ga.injuk.security.repositories.UserRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class IndexController(
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

    // 스프링 시큐리티가 요 친구를 가로챔 - Security Config 생성 후 가로채지 않음!
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
        userRepository.save(User.from(user))

        return "redirect:/loginForm"
    }
}