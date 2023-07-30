package com.example.minishophub.domain.user.controller.dto.request

data class MailRequest(
    val email: String,
    val title: String = "title",
    val content: String = "content",
)