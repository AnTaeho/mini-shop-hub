package com.example.minishophub.domain.user.controller.dto.response

import com.example.minishophub.domain.user.persistence.buyer.Buyer
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
        fun of(buyer: Buyer): UserResponse {
            return UserResponse(
                userId = buyer.id!!,
                nickname = buyer.nickname,
                email = buyer.email,
                age = buyer.age,
                city = buyer.city,
                role = buyer.role
            )
        }
    }
}