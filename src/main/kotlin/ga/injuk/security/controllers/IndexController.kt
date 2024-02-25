package ga.injuk.security.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {

    @GetMapping(path = ["", "/"])
    fun index(): String = "index";
}