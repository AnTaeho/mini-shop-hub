package com.example.minishophub.domain.user.persistence.seller

import org.springframework.data.jpa.repository.JpaRepository

interface SellerRepository : JpaRepository<Seller, Long> {

    fun findByBusinessRegistrationNumber(businessRegistrationNumber: String): Seller?
    fun findByEmail(email: String): Seller?
    fun findByNickname(nickname: String): Seller?

}