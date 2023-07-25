package com.example.minishophub.domain.user.controller

import com.example.minishophub.domain.user.controller.dto.request.UserJoinRequest
import com.example.minishophub.domain.user.controller.dto.request.UserUpdateRequest
import com.example.minishophub.domain.user.controller.dto.response.UserResponse
import com.example.minishophub.domain.user.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
class UserApiController (
    private val userService: UserService,
) {

    @PostMapping("/sign-up")
    fun join(@RequestBody joinRequest: UserJoinRequest): String {
        userService.join(joinRequest)
        return "회원 가입 성공"
    }

    @GetMapping("/user/{userId}")
    fun findUser(@PathVariable userId: Long): UserResponse {
        val find = userService.find(userId)
        return UserResponse.of(find)
    }

    @PutMapping("/user/{userId}")
    fun updateUser(@PathVariable userId: Long,
                   @RequestBody updateRequest: UserUpdateRequest
    ) {
        userService.update(userId, updateRequest)
    }

    @DeleteMapping("/user/{userId}")
    fun deleteUser(@PathVariable userId: Long) {
        userService.delete(userId)
    }

    @GetMapping("/jwt-test")
    fun jwtTest(): String? = "jwtTest 요청 성공"

}