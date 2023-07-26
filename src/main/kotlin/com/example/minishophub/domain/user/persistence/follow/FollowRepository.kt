package com.example.minishophub.domain.user.persistence.follow

import com.example.minishophub.domain.shop.persistence.Shop
import com.example.minishophub.domain.user.persistence.buyer.Buyer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying

interface FollowRepository : JpaRepository<Follow, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    fun deleteByBuyerAndShop(buyer: Buyer, shop: Shop)

    fun existsByBuyerAndShop(buyer: Buyer, shop: Shop): Boolean
}