package com.example.minishophub.global.logout

import com.example.minishophub.domain.user.persistence.user.UserRepository
import com.example.minishophub.global.jwt.service.JwtService
import com.example.minishophub.global.util.fail
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.transaction.annotation.Transactional

@Transactional
class CustomLogoutSuccessHandler(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
) : LogoutSuccessHandler {

    override fun onLogoutSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {

        response.setHeader("Authorization", null)

        val userDetails = authentication.principal as UserDetails
        val email = userDetails.username
        val user = userRepository.findByEmail(email) ?: fail()
        user.refreshToken = null

        val fakeAccessToken = jwtService.createFakeAccessToken()
        jwtService.sendAccessAndRefreshToken(response, fakeAccessToken, null)
    }

}