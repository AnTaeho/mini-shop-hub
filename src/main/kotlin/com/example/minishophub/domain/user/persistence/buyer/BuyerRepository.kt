package com.example.minishophub.domain.user.persistence.buyer

import com.example.minishophub.domain.user.persistence.SocialType
import org.springframework.data.jpa.repository.JpaRepository

interface BuyerRepository : JpaRepository<Buyer, Long> {
    fun findByEmail(email: String): Buyer?
    fun findByNickname(nickname: String): Buyer?
    fun findByRefreshToken(refreshToken: String): Buyer?
    fun findBySocialTypeAndSocialId(socialType: SocialType, socialId: String): Buyer?
}