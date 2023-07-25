package com.example.minishophub.global.login.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.util.StreamUtils
import java.nio.charset.StandardCharsets

class CustomJsonUsernamePasswordAuthenticationFilter(
    private val objectMapper: ObjectMapper,
) : AbstractAuthenticationProcessingFilter(DEFAULT_LOGIN_PATH_REQUEST_MATCHER) {

    companion object {
        private const val DEFAULT_LOGIN_REQUEST_URL = "/login"
        private const val HTTP_METHOD = "POST"
        const val CONTENT_TYPE = "application/json"
        const val USERNAME_KEY = "email"
        const val PASSWORD_KEY = "password"

        // "/login" & POST 요청만 매칭
        val DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD)
    }

    /**
     * StreamUtils 를 통해서 request 에서 messageBody(JSON) 추출
     * messageBody 를 Map 으로 변환 후 추출
     * 추출한 email, password 를  UsernamePasswordAuthenticationToken 의 파라미터로 대입
     */
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        // JSON 요청만 처리
        if (request.contentType == null || !request.contentType.equals(CONTENT_TYPE)) {
            throw AuthenticationServiceException("Authentication Content-Type not supported: " + request.contentType)
        }

        val messageBody = StreamUtils.copyToString(request.inputStream, StandardCharsets.UTF_8)

        val usernamePasswordMap = objectMapper.readValue(messageBody, MutableMap::class.java)

        val email = usernamePasswordMap[USERNAME_KEY]
        val password = usernamePasswordMap[PASSWORD_KEY]

        val authRequest = UsernamePasswordAuthenticationToken(email, password)
        return this.authenticationManager.authenticate(authRequest)
    }
}