package com.example.minishophub.domain.user.persistence

enum class UserRole(
    val key: String
) {
    GUEST("ROLE_GUEST"),
    USER("ROLE_USER"),
    SELLER_AUTHENTICATION_REQUIRED("ROLE_SELLER_AUTHENTICATION_REQUIRED"),
    SELLER_AUTHENTICATION_DONE("ROLE_SELLER_AUTHENTICATION_DONE"),
    ADMIN("ROLE_ADMIN")
}