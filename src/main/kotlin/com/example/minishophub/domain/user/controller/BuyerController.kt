package com.example.minishophub.domain.user.controller

import com.example.minishophub.domain.user.controller.dto.request.*
import com.example.minishophub.domain.user.controller.dto.response.UserResponse
import com.example.minishophub.domain.user.service.BuyerService
import com.example.minishophub.domain.user.service.MailService
import com.example.minishophub.global.jwt.service.JwtService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
class BuyerController (
    private val buyerService: BuyerService,
    private val mailService: MailService,
    private val jwtService: JwtService,
) {

    @PostMapping("/sign-up")
    fun join(@RequestBody joinRequest: UserJoinRequest): String {
        buyerService.join(joinRequest)
        return "회원 가입 성공"
    }

    @PostMapping("/follow/{shopId}")
    fun followShop(@PathVariable shopId: Long,
                   @AuthenticationPrincipal userDetails: UserDetails,
    ) = buyerService.followShop(shopId, userDetails.username)

    @GetMapping("/user/{userId}")
    fun findUser(@PathVariable userId: Long): UserResponse = UserResponse.of(buyerService.find(userId))

    @PutMapping("/user/{userId}")
    fun updateUser(@PathVariable userId: Long,
                   @RequestBody updateRequest: UserUpdateRequest
    ) = buyerService.update(userId, updateRequest)

    @PutMapping("/user/password/{email}")
    fun changePassword(@AuthenticationPrincipal userDetails: UserDetails,
                       @RequestBody password: String,
                       @PathVariable email: String) {
        if (email != userDetails.username) {
            throw IllegalArgumentException("본인이 아닙니다.")
        }
        buyerService.changePassword(userDetails.username, password)
    }

    @PutMapping("/oauth")
    fun updateOAuthInfo(@AuthenticationPrincipal userDetails: UserDetails,
                        @RequestBody updateRequest: OAuth2UserUpdateRequest
    ) = buyerService.updateOAuth2(updateRequest, userDetails.username!!)

    @DeleteMapping("/user/{userId}")
    fun deleteUser(@PathVariable userId: Long) = buyerService.delete(userId)

    @GetMapping("/change-password")
    fun changePassword(@RequestBody mailRequest: MailRequest,
                       @AuthenticationPrincipal userDetails: UserDetails) {
        if (mailRequest.email != userDetails.username) {
            throw IllegalArgumentException("본인 인증된 이메일이 아닙니다.")
        }
        mailService.sendResettingPasswordMail(mailRequest)
    }

    @GetMapping("/mail/auth")
    fun authMail(@RequestBody mailAuthRequest: MailAuthRequest,
                 @AuthenticationPrincipal userDetails: UserDetails) {
        mailService.sendAuthMail(mailAuthRequest)
    }

    @PostMapping("/mail/auth/{accessToken}")
    fun authorizeUserByMail(@PathVariable accessToken: String,
                            response: HttpServletResponse) {
        val email = jwtService.extractEmail(accessToken)!!
        buyerService.authorizeUser(email, accessToken)

    }

    @GetMapping("/jwt-test")
    fun jwtTest(): String? = "jwtTest 요청 성공"

}