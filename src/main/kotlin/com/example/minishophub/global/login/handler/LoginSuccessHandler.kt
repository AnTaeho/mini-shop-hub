package com.example.minishophub.global.login.handler

import com.example.minishophub.domain.user.persistence.UserRepository
import com.example.minishophub.global.jwt.service.JwtService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler

class LoginSuccessHandler(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val email = extractUsername(authentication)
        val accessToken = jwtService.createAccessToken(email)
        val refreshToken = jwtService.createRefreshToken()

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken)

        val user = userRepository.findByEmail(email)
        if (user != null) {
            user.updateRefreshToken(refreshToken)
            userRepository.saveAndFlush(user)
        }

        println("로그인에 성공했습니다. 이메일 : $email")
        println("로그인에 성공했습니다. AccessToken : $accessToken")
    }

    private fun extractUsername(authentication: Authentication): String {
        val userDetails = authentication.principal as UserDetails
        return userDetails.username
    }
}