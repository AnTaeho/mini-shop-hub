package com.example.minishophub.domain.user.controller

import com.example.minishophub.domain.user.controller.dto.request.MailRequest
import com.example.minishophub.domain.user.controller.dto.request.OAuth2UserUpdateRequest
import com.example.minishophub.domain.user.controller.dto.request.UserJoinRequest
import com.example.minishophub.domain.user.controller.dto.request.UserUpdateRequest
import com.example.minishophub.domain.user.controller.dto.response.UserResponse
import com.example.minishophub.domain.user.service.BuyerService
import com.example.minishophub.domain.user.service.MailService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
class BuyerController (
    private val buyerService: BuyerService,
    private val mailService: MailService,
) {

    @PostMapping("/sign-up")
    fun join(@RequestBody joinRequest: UserJoinRequest): String {
        buyerService.join(joinRequest)
        return "회원 가입 성공"
    }

    @PostMapping("/follow/{shopId}")
    fun followShop(@PathVariable shopId: Long,
                   @AuthenticationPrincipal userDetails: UserDetails,
    ) {
        buyerService.followShop(shopId, userDetails.username)
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

    @PutMapping("/user/password")
    fun changePassword(@AuthenticationPrincipal userDetails: UserDetails,
                       @RequestBody password: String) {
        buyerService.changePassword(userDetails.username, password)
    }

    @PutMapping("/oauth")
    fun updateOAuthInfo(@AuthenticationPrincipal userDetails: UserDetails,
                        @RequestBody updateRequest: OAuth2UserUpdateRequest) {
        val email = userDetails.username
        buyerService.updateOAuth2(updateRequest, email!!)
    }

    @DeleteMapping("/user/{userId}")
    fun deleteUser(@PathVariable userId: Long) {
        buyerService.delete(userId)
    }

    @PutMapping("/change-password")
    fun changePassword(@RequestBody mailRequest: MailRequest) {
        mailService.sendResettingPasswordMail(mailRequest)
    }

    @GetMapping("/jwt-test")
    fun jwtTest(): String? = "jwtTest 요청 성공"

}