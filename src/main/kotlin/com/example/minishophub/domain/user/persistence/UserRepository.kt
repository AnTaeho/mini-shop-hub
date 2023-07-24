package com.example.minishophub.domain.user.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByNickname(nickname: String): User?
    fun findByRefreshToken(refreshToken: String): User?
    fun findBySocialTypeAndSocialId(socialType: SocialType, socialId: String): User?
}