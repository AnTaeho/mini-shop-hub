package com.example.minishophub.domain.user.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/login-home")
    fun loginHome(): String {
        return "home"
    }

}