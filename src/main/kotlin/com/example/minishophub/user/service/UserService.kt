package com.example.minishophub.user.service

import com.example.minishophub.user.controller.dto.UserJoinRequest
import com.example.minishophub.user.persistence.User
import com.example.minishophub.user.persistence.UserRepository
import com.example.minishophub.user.persistence.UserRole
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun join(joinRequest: UserJoinRequest) {

        if (userRepository.findByEmail(joinRequest.email) != null) {
            throw IllegalArgumentException("이미 존재하는 이메일 입니다.")
        }
        if (userRepository.findByNickname(joinRequest.nickname) != null) {
            throw IllegalArgumentException("이미 존재하는 닉네임 입니다.")
        }

        val user = User(
            email = joinRequest.email,
            password = joinRequest.password,
            nickname = joinRequest.nickname,
            age = joinRequest.age,
            city = joinRequest.city,
            role = UserRole.USER,
        )

        user.passwordEncode(passwordEncoder)
        userRepository.save(user)

    }

}