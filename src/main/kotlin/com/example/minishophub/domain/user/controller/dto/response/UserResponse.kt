package com.example.minishophub.domain.user.controller.dto.response

import com.example.minishophub.domain.user.persistence.User
import com.example.minishophub.domain.user.persistence.UserRole

data class UserResponse(
    val userId: Long,
    val nickname: String,
    val email: String,
    val age: Int,
    val city: String,
    val role: UserRole,
) {
    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(
                userId = user.id!!,
                nickname = user.nickname!!,
                email = user.email,
                age = user.age,
                city = user.city,
                role = user.role
            )
        }
    }
}