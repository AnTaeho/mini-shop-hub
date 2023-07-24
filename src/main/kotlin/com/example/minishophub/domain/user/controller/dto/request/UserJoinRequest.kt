package com.example.minishophub.domain.user.controller.dto.request

data class UserJoinRequest (
    val email: String,
    val password: String,
    val nickname: String,
    val age: Int,
    val city: String,
)