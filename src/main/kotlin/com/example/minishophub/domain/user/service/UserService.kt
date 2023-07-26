package com.example.minishophub.domain.user.service

import com.example.minishophub.domain.user.controller.dto.request.OAuth2UserUpdateRequest
import com.example.minishophub.domain.user.controller.dto.request.UserJoinRequest
import com.example.minishophub.domain.user.controller.dto.request.UserUpdateRequest
import com.example.minishophub.domain.user.persistence.User
import com.example.minishophub.domain.user.persistence.UserRepository
import com.example.minishophub.domain.user.persistence.UserRole
import com.example.minishophub.global.jwt.service.JwtService
import com.example.minishophub.global.util.fail
import com.example.minishophub.global.util.findByIdOrThrow
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService (
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
) {

    @Transactional
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

    fun find(userId: Long): User {
        return userRepository.findByIdOrThrow(userId)
    }

    @Transactional
    fun update(userId: Long, updateRequest: UserUpdateRequest) {
        val user = userRepository.findByIdOrThrow(userId)

        if (userRepository.findByEmail(updateRequest.email) != null) {
            throw IllegalArgumentException("이미 존재하는 이메일 입니다.")
        }
        if (userRepository.findByNickname(updateRequest.nickname) != null) {
            throw IllegalArgumentException("이미 존재하는 닉네임 입니다.")
        }

        user.update(updateRequest)
    }

    @Transactional
    fun delete(userId: Long) {
        userRepository.deleteById(userId)
    }

    @Transactional
    fun updateOAuth2(updateRequest: OAuth2UserUpdateRequest, email: String) {
        val user = userRepository.findByEmail(email)!!
        user.updateOAuth(updateRequest)
    }

}
