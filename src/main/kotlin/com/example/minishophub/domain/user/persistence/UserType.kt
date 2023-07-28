package com.example.minishophub.domain.user.persistence

enum class UserType (
    val key: String
) {
    BUYER("ROLE_BUYER"),
    SELLER("ROLE_SELLER"),
}