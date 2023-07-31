package com.example.minishophub

import com.example.minishophub.domain.user.persistence.UserRole
import com.example.minishophub.domain.user.persistence.user.User
import com.example.minishophub.domain.user.persistence.user.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class InitData(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    @PostConstruct
    fun makeAdminUser() {
        val user = User(
            email = "an981022@naver.com",
            password = "admin123",
            nickname = "antaeho",
            age = 26,
            city = "seoul",
            role = UserRole.ADMIN,
        )
        user.passwordEncode(passwordEncoder)
        userRepository.save(user)
    }
}