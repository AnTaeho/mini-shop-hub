package com.example.minishophub.user.controller

import com.example.minishophub.user.controller.dto.request.UserJoinRequest
import com.example.minishophub.user.controller.dto.request.UserUpdateRequest
import com.example.minishophub.user.controller.dto.response.UserResponse
import com.example.minishophub.user.service.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController (
    private val userService: UserService,
) {

    @PostMapping("/join")
    fun join(@RequestBody joinRequest: UserJoinRequest): String {
        userService.join(joinRequest)
        return "회원 가입 성공"
    }

    @GetMapping("/{userId}")
    fun findUser(@PathVariable userId: Long): UserResponse {
        val find = userService.find(userId)
        return UserResponse.of(find)
    }

    @PutMapping("/{userId}")
    fun updateUser(@PathVariable userId: Long,
                   @RequestBody updateRequest: UserUpdateRequest) {
        userService.update(userId, updateRequest)
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: Long) {
        userService.delete(userId)
    }
}