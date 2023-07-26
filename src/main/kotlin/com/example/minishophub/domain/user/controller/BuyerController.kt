package com.example.minishophub.domain.user.controller

import com.example.minishophub.domain.user.controller.dto.request.OAuth2UserUpdateRequest
import com.example.minishophub.domain.user.controller.dto.request.UserJoinRequest
import com.example.minishophub.domain.user.controller.dto.request.UserUpdateRequest
import com.example.minishophub.domain.user.controller.dto.response.UserResponse
import com.example.minishophub.domain.user.service.BuyerService
import com.example.minishophub.global.jwt.service.JwtService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@RestController
class BuyerController (
    private val buyerService: BuyerService,
    private val jwtService: JwtService,
) {

    @PostMapping("/sign-up")
    fun join(@RequestBody joinRequest: UserJoinRequest): String {
        buyerService.join(joinRequest)
        return "회원 가입 성공"
    }

    @GetMapping("/user/{userId}")
    fun findUser(@PathVariable userId: Long): UserResponse {
        val find = buyerService.find(userId)
        return UserResponse.of(find)
    }

    @PutMapping("/user/{userId}")
    fun updateUser(@PathVariable userId: Long,
                   @RequestBody updateRequest: UserUpdateRequest
    ) {
        buyerService.update(userId, updateRequest)
    }

    @PutMapping("/oauth")
    fun updateOAuthInfo(request: HttpServletRequest, @RequestBody updateRequest: OAuth2UserUpdateRequest) {
        val email = jwtService.extractEmail(request)
        buyerService.updateOAuth2(updateRequest, email!!)
    }

    @DeleteMapping("/user/{userId}")
    fun deleteUser(@PathVariable userId: Long) {
        buyerService.delete(userId)
    }

    @GetMapping("/jwt-test")
    fun jwtTest(): String? = "jwtTest 요청 성공"

}