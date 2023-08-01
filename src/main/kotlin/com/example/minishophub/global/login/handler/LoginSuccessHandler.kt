package com.example.minishophub.global.login.handler

import com.example.minishophub.domain.user.persistence.user.UserRepository
import com.example.minishophub.global.jwt.service.JwtService
import com.example.minishophub.global.util.fail
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.transaction.annotation.Transactional

@Transactional
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

        val user = userRepository.findByEmail(email) ?: fail()
        user.updateRefreshToken(refreshToken)
        userRepository.save(user)
    }

    private fun extractUsername(authentication: Authentication): String {
        val userDetails = authentication.principal as UserDetails
        return userDetails.username
    }
}