package com.example.minishophub.global.jwt.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.minishophub.domain.user.persistence.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService (
    @Value("\${jwt.secretKey}")
    private var secretKey: String,

    @Value("\${jwt.access.expiration}")
    private val accessTokenExpirationPeriod: Long,

    @Value("\${jwt.refresh.expiration}")
    private val refreshTokenExpirationPeriod: Long,

    @Value("\${jwt.access.header}")
    val accessHeader: String,

    @Value("\${jwt.refresh.header}")
    val refreshHeader: String,

    private val userRepository: UserRepository,

) {
    companion object {
        const val ACCESS_TOKEN_SUBJECT: String = "AccessToken"
        const val REFRESH_TOKEN_SUBJECT: String = "RefreshToken"
        const val EMAIL_CLAIM: String = "email"
        const val BEARER: String = "Bearer "
    }

    /**
     * AccessToken 생성
     */
    fun createAccessToken(email: String): String {
        val now = Date()
        return JWT.create()
            .withSubject(ACCESS_TOKEN_SUBJECT)
            .withExpiresAt(Date(now.time + accessTokenExpirationPeriod))

            //클레임에 추가적인 정보 추가 가능
            //.withClaim("이름", 내용)
            .withClaim(EMAIL_CLAIM, email)
            .sign(Algorithm.HMAC512(secretKey))
    }

    /**
     * RefreshToken 생성
     * RefreshToken 은 Claim 필요 없음
     */
    fun createRefreshToken(): String {
        val now = Date()
        return JWT.create()
            .withSubject(REFRESH_TOKEN_SUBJECT)
            .withExpiresAt(Date(now.time + refreshTokenExpirationPeriod))
            .sign(Algorithm.HMAC512(secretKey))
    }

    /**
     * AccessToken 헤더에 실어서 보내기
     * OAuth2 인증에 사용
     */
    fun sendAccessToken(response: HttpServletResponse, accessToken: String) {
        response.status = HttpServletResponse.SC_OK
        response.setHeader(accessHeader, accessToken)
    }

    /**
     * AccessToken + RefreshToken 헤더에 실어서 보내기
     * JWT(일반 로그인) 인증에 사용
     */
    fun sendAccessAndRefreshToken(
        response: HttpServletResponse,
        accessToken: String,
        refreshToken: String?
    ) {
        response.status = HttpServletResponse.SC_OK
        response.setHeader(accessHeader, accessToken)
        response.setHeader(refreshHeader, refreshToken)
    }

    /**
     * 헤더에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    fun extractRefreshToken(request: HttpServletRequest): String? {
        val header = request.getHeader(refreshHeader) ?: return null
        return if (header.startsWith(BEARER)) {
            header.replace(BEARER, "")
        } else null
    }

    /**
     * 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    fun extractAccessToken(request: HttpServletRequest): String? {
        val header = request.getHeader(accessHeader) ?: return null
        return if (header.startsWith(BEARER)) {
            header.replace(BEARER, "")
        } else null
    }

    /**
     * AccessToken 에서 Email 추출
     * 추출 전에 JWT.require()로 검증기 생성
     * verify 로 AcㅊessToken 검증 후
     * 유효하다면 getClaim()으로 이메일 추출
     * 유효하지 않다면 빈 Optional 객체 반환
     */
    fun extractEmail(accessToken: String): String? {
        return try {
            JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(accessToken)
                .getClaim(EMAIL_CLAIM)
                .asString()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * RefreshToken DB 저장(업데이트)
     */
    fun updateRefreshToken(email: String, refreshToken: String) {
        val findUser = userRepository.findByEmail(email)
        if (findUser == null) {
            throw IllegalArgumentException("일치하는 회원이 없습니다.")
        } else {
            findUser.updateRefreshToken(refreshToken)
        }
    }

    fun isTokenValid(token: String): Boolean {
        return try {
            JWT.require(Algorithm.HMAC512(secretKey))
                .build().verify(token)
            true
        } catch (e: Exception) {
            false
        }
    }
}