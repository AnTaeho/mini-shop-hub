package com.example.minishophub.user.controller

import com.example.minishophub.user.controller.dto.UserJoinRequest
import com.example.minishophub.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController (
    private val userService: UserService,
) {

    @PostMapping("/user/join")
    fun join(@RequestBody joinRequest: UserJoinRequest): String {
        userService.join(joinRequest)
        return "회원 가입 성공"
    }
}