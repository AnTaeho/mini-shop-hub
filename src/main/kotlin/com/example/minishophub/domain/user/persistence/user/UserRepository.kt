package com.example.minishophub.domain.user.persistence.user

import com.example.minishophub.domain.user.persistence.SocialType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByRefreshToken(refreshToken: String): User?
    fun findBySocialTypeAndSocialId(socialType: SocialType, socialId: String): User?

    @Query("select u.email from User u where u.email = :email")
    fun findEmail(@Param("email")email: String): String?

    @Query("select u.nickname from User u where u.nickname = :nickname")
    fun findNickname(@Param("nickname") nickname: String): String?
}