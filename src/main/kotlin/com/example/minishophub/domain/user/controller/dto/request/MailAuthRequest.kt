package com.example.minishophub.domain.user.controller.dto.request

class MailAuthRequest(
    val email: String,
    val title: String = "이메일 인증 메일",
    val content: String = "이메일 인증을 완료하려면 다음 링크를 클릭해주세요. \n" +
            "http://localhost:8080/mail/auth/",
)
