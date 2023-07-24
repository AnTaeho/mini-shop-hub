package com.example.minishophub.user.controller.dto.request

data class UserUpdateRequest (
    val email: String,
    val nickname: String,
    val age: Int,
    val city: String,
)