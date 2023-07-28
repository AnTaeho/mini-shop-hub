package com.example.minishophub.global.config

import com.example.minishophub.domain.user.persistence.UserRole
import com.example.minishophub.domain.user.persistence.UserType
import com.example.minishophub.domain.user.persistence.user.UserRepository
import com.example.minishophub.global.jwt.filter.JwtAuthenticationProcessingFilter
import com.example.minishophub.global.jwt.service.JwtService
import com.example.minishophub.global.login.filter.CustomJsonUsernamePasswordAuthenticationFilter
import com.example.minishophub.global.login.handler.LoginFailureHandler
import com.example.minishophub.global.login.handler.LoginSuccessHandler
import com.example.minishophub.global.login.service.LoginService
import com.example.minishophub.global.logout.CustomLogoutSuccessHandler
import com.example.minishophub.global.oauth.handler.OAuth2LoginFailureHandler
import com.example.minishophub.global.oauth.handler.OAuth2LoginSuccessHandler
import com.example.minishophub.global.oauth.service.CustomOAuth2UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutFilter

@Configuration
@EnableWebSecurity
class SecurityConfig (

    private val loginService: LoginService,
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val objectMapper: ObjectMapper,
    private val oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler,
    private val oAuth2LoginFailureHandler: OAuth2LoginFailureHandler,
    private val customOAuth2UserService: CustomOAuth2UserService,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf(CsrfConfigurer<HttpSecurity>::disable)
            .formLogin(FormLoginConfigurer<HttpSecurity>::disable)
            .httpBasic(HttpBasicConfigurer<HttpSecurity>::disable)
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers("/","/css/**","/images/**","/js/**","/favicon.ico","/h2-console/**").permitAll()
                it.requestMatchers("/sign-up").permitAll()
                it.requestMatchers("/owner/shop").hasRole(UserRole.SELLER_AUTHENTICATION_REQUIRED.name)
                it.requestMatchers("/owner/**").hasRole(UserRole.SELLER_AUTHENTICATION_DONE.name)
                it.requestMatchers("/admin/**").hasRole(UserRole.ADMIN.name)
                it.anyRequest().authenticated()
            }
            .oauth2Login {
                it.successHandler(oAuth2LoginSuccessHandler)
                it.failureHandler(oAuth2LoginFailureHandler)
                it.userInfoEndpoint { user -> user
                    .userService(customOAuth2UserService)
                }
            }
            .logout {
                it.logoutUrl("/logout")
//                it.logoutSuccessHandler(CustomLogoutSuccessHandler(jwtService, userRepository))
//                it.clearAuthentication(true)
                it.logoutSuccessUrl("/jwt-test").permitAll()
            }
            .addFilterBefore(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter::class.java)
            .addFilterAfter(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun authenticationManager(): AuthenticationManager? {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder())
        provider.setUserDetailsService(loginService)
        return ProviderManager(provider)
    }

    @Bean
    fun loginSuccessHandler(): LoginSuccessHandler? {
        return LoginSuccessHandler(jwtService, userRepository)
    }

    /**
     * 로그인 실패 시 호출되는 LoginFailureHandler 빈 등록
     */
    @Bean
    fun loginFailureHandler(): LoginFailureHandler? {
        return LoginFailureHandler()
    }

    @Bean
    fun customJsonUsernamePasswordAuthenticationFilter(): CustomJsonUsernamePasswordAuthenticationFilter? {
        val customJsonUsernamePasswordLoginFilter = CustomJsonUsernamePasswordAuthenticationFilter(objectMapper)
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager())
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler())
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler())
        return customJsonUsernamePasswordLoginFilter
    }

    @Bean
    fun jwtAuthenticationProcessingFilter(): JwtAuthenticationProcessingFilter? {
        return JwtAuthenticationProcessingFilter(jwtService, userRepository)
    }
}