package com.example.minishophub.domain.user.persistence.user

import com.example.minishophub.domain.user.persistence.SocialType
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByRefreshToken(refreshToken: String): User?
    fun existsByEmail(email: String): Boolean
    fun existsByNickname(nickname: String): Boolean
    fun findBySocialTypeAndSocialId(socialType: SocialType, socialId: String): User?
}