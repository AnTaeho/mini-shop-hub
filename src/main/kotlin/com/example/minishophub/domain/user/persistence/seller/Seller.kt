package com.example.minishophub.domain.user.persistence.seller

import com.example.minishophub.domain.shop.persistence.Shop
import com.example.minishophub.domain.user.controller.dto.request.UserUpdateRequest
import com.example.minishophub.domain.user.persistence.SocialType
import com.example.minishophub.domain.user.persistence.UserRole
import jakarta.persistence.*

@Entity
class Seller (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    var id: Long = 0,

    var email: String,
    var password: String,
    var nickname: String,
    var age: Int,
    var city: String,
    var socialId: String? = null,
    var refreshToken: String? = null,
    var businessRegistrationNumber: String,

    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.SELLER_AUTHENTICATION_REQUIRED,

    @Enumerated(EnumType.STRING)
    var socialType: SocialType = SocialType.NO_SOCIAL,

    @OneToOne(fetch = FetchType.LAZY)
    var myShop: Shop,
) {
    init {
        myShop.ownerId = id
    }

    fun registerShop(shop: Shop) {
        this.myShop = shop
        this.role = UserRole.SELLER_AUTHENTICATION_DONE
    }

    fun update(updateRequest: UserUpdateRequest) {
        this.email = updateRequest.email
        this.nickname = updateRequest.nickname
        this.age = updateRequest.age
        this.city = updateRequest.city
    }
}