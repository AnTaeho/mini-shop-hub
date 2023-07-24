package com.example.minishophub.user.controller.dto

data class UserJoinRequest (
    val email: String,
    val password: String,
    val nickname: String,
    val age: Int,
    val city: String,
)