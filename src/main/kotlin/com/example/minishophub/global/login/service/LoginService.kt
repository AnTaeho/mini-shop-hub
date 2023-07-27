package com.example.minishophub.global.login.service

import com.example.minishophub.domain.user.persistence.user.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    private val log = KotlinLogging.logger { }

    override fun loadUserByUsername(email: String): UserDetails {

        log.info { "LoginService - loadUserByUsername 시작" }

        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("해당 이메일이 존재하지 않습니다.")

        log.info { "LoginService - loadUserByUsername 종료" }

        return User.builder()
            .username(user.email)
            .password(user.password)
            .roles(user.role.name)
            .build()
    }


}