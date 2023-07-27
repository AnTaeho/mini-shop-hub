package com.example.minishophub.domain.user.persistence.follow

import com.example.minishophub.domain.shop.persistence.Shop
import com.example.minishophub.domain.user.persistence.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying

interface FollowRepository : JpaRepository<Follow, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    fun deleteByUserAndShop(user: User, shop: Shop)

    fun existsByUserAndShop(user: User, shop: Shop): Boolean

    fun findAllByShop(shop: Shop): MutableList<Follow>
}