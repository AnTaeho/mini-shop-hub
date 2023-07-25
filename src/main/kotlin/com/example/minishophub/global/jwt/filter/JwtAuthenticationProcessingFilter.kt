package com.example.minishophub.global.jwt.filter

import com.example.minishophub.domain.user.persistence.User
import com.example.minishophub.domain.user.persistence.UserRepository
import com.example.minishophub.global.jwt.service.JwtService
import com.example.minishophub.global.jwt.util.PasswordUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationProcessingFilter(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val authoritiesMapper: GrantedAuthoritiesMapper = NullAuthoritiesMapper()
) : OncePerRequestFilter() {

    companion object {
        const val NO_CHECK_URL = "/login"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // /login 으로 요청 오면 다음 필터 호출 후 탈출
        if (request.requestURI.equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response)
            return
        }

        val refreshToken = jwtService.extractRefreshToken(request)
        if (refreshToken != null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken)
            return
        }

        checkAccessTokenAndAuthentication(request, response, filterChain)
    }

    private fun checkRefreshTokenAndReIssueAccessToken(response: HttpServletResponse,
                                                       refreshToken: String) {
        val user = userRepository.findByRefreshToken(refreshToken)
        if (user != null) {
            val reIssueRefreshToken = reIssueRefreshToken(user)
            jwtService.sendAccessAndRefreshToken(
                response,
                jwtService.createAccessToken(user.email),
                reIssueRefreshToken
            )
        }
    }

    private fun reIssueRefreshToken(user: User): String {
        val reIssuedRefreshToken = jwtService.createRefreshToken()
        user.updateRefreshToken(reIssuedRefreshToken)
        userRepository.saveAndFlush(user)
        return reIssuedRefreshToken
    }

    private fun checkAccessTokenAndAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val accessToken = jwtService.extractAccessToken(request)
        if (accessToken != null) {
            val email = jwtService.extractEmail(accessToken)
            if (email != null) {
                val user = userRepository.findByEmail(email)
                saveAuthentication(user!!)
            }
        }
        filterChain.doFilter(request, response)
    }

    fun saveAuthentication(user: User) {
        var password = user.password
        if (password != null) {
            password = PasswordUtil.generateRandomPassword()
        }

        val userDetailsUser = org.springframework.security.core.userdetails.User.builder()
            .username(user.email)
            .password(password)
            .roles(user.role.name)
            .build()

        val authentication = UsernamePasswordAuthenticationToken(
            userDetailsUser,
            null,
            authoritiesMapper.mapAuthorities(userDetailsUser.authorities)
        )

        SecurityContextHolder.getContext().authentication = authentication
    }

}