package ga.injuk.security.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class IndexController {

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

    // 스프링 시큐리티가 요 친구를 가로챔
    @GetMapping("/login")
    @ResponseBody
    fun login(): String {
        return "login"
    }

    @GetMapping("/join")
    @ResponseBody
    fun join(): String {
        return "join"
    }

    @GetMapping("/joinProc")
    @ResponseBody
    fun joinProc(): String {
        return "회원가입 완료됨"
    }
}