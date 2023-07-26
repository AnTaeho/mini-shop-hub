package com.example.minishophub.domain.shop.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface ShopRepository : JpaRepository<Shop, Long> {
    fun findByBusinessRegistrationNumber(businessRegistrationNumber: String): Shop?
}