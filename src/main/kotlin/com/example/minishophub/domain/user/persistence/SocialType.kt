package com.example.minishophub.domain.user.persistence

enum class SocialType(
    val type: String,
) {
    KAKAO("kakao"),
    NAVER("naver"),
    GOOGLE("google"),
    NO_SOCIAL("no-social"),
}