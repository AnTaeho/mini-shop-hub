package com.example.minishophub.global.jwt.filter

import com.example.minishophub.domain.user.persistence.buyer.Buyer
import com.example.minishophub.domain.user.persistence.buyer.BuyerRepository
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

/**
 * JWT 인증 필터
 * "/login" 이외의 URI 요청이 왔을 때 처리
 *
 * 기본적으로 AccessToken 만 사용
 * AccessToken 만료시 RefreshToken 같이 사용
 */
class JwtAuthenticationProcessingFilter(
    private val jwtService: JwtService,
    private val buyerRepository: BuyerRepository,
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

        // RefreshToken 있을 시에 같이 전송하고, AccessToken 재발급 후 탈출
        val refreshToken = jwtService.extractRefreshToken(request)
        if (refreshToken != null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken)
            return
        }

        checkAccessTokenAndAuthentication(request, response, filterChain)
    }

    /**
     * RefreshToken 검증후 AccessToken, RefreshToken 재발급
     */
    private fun checkRefreshTokenAndReIssueAccessToken(response: HttpServletResponse,
                                                       refreshToken: String) {
        val user = buyerRepository.findByRefreshToken(refreshToken)
        if (user != null) {
            val reIssueRefreshToken = reIssueRefreshToken(user)
            jwtService.sendAccessAndRefreshToken(
                response,
                jwtService.createAccessToken(user.email),
                reIssueRefreshToken
            )
        }
    }

    private fun reIssueRefreshToken(buyer: Buyer): String {
        val reIssuedRefreshToken = jwtService.createRefreshToken()
        buyer.updateRefreshToken(reIssuedRefreshToken)
        buyerRepository.saveAndFlush(buyer)
        return reIssuedRefreshToken
    }

    private fun checkAccessTokenAndAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val accessToken = jwtService.extractAccessToken(request)
        val email = jwtService.extractEmail(accessToken)
        if (email != null) {
            val user = buyerRepository.findByEmail(email)
            saveAuthentication(user!!)
        }
        filterChain.doFilter(request, response)
    }

    private fun saveAuthentication(buyer: Buyer) {
        val password: String = PasswordUtil.generateRandomPassword()

        val userDetailsUser = org.springframework.security.core.userdetails.User.builder()
            .username(buyer.email)
            .password(password)
            .roles(buyer.role.name)
            .build()

        val authentication = UsernamePasswordAuthenticationToken(
            userDetailsUser,
            null,
            authoritiesMapper.mapAuthorities(userDetailsUser.authorities)
        )

        SecurityContextHolder.getContext().authentication = authentication
    }

}