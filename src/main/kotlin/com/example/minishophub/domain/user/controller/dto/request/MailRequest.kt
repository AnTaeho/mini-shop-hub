package com.example.minishophub.domain.user.controller.dto.request

data class MailRequest(
    val email: String,
    val title: String = "비밀 번호 변경 안내",
    val content: String = "비밀번호 변경을 원하시면 다음 링크를 클릭해주세요." +
            "http://localhost:8080/user/password/",
)