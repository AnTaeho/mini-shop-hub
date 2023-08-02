package com.example.minishophub.domain.user.persistence.user

import com.example.minishophub.domain.base.BaseEntity
import com.example.minishophub.domain.notification.persistence.Notification
import com.example.minishophub.domain.shop.persistence.Shop
import com.example.minishophub.domain.user.controller.dto.request.OAuth2UserUpdateRequest
import com.example.minishophub.domain.user.controller.dto.request.UserUpdateRequest
import com.example.minishophub.domain.user.persistence.SocialType
import com.example.minishophub.domain.user.persistence.UserRole
import com.example.minishophub.domain.user.persistence.UserType
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "USERS", indexes = [Index(name = "idx_email_nickname_PK", columnList = "email, nickname, user_id")])
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long = 0L,

    var email: String,
    var providerEmail: String = "only-social-login",
    var password: String,
    var nickname: String,
    var age: Int,
    var profile: String = "no-image",
    var city: String,
    var socialId: String? = null,
    var refreshToken: String? = null,

    // 사업자 번호 발급 받는 방법에 따라 구현이 바껴야 할 수도 있다.
    var businessRegistrationNumber: String = "only-seller",

    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.GUEST,

    @Enumerated(EnumType.STRING)
    var socialType: SocialType = SocialType.NO_SOCIAL,

    @Enumerated(EnumType.STRING)
    var userType: UserType = UserType.BUYER,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "shop_id")
    var myShop: Shop? = null,

    @OneToMany(mappedBy = "notifiedUser", cascade = [CascadeType.REMOVE])
    var notifications: MutableList<Notification> = mutableListOf(),

) : BaseEntity() {

    fun passwordEncode(passwordEncoder: PasswordEncoder) {
        this.password = passwordEncoder.encode(this.password)
    }

    fun updateRefreshToken(updateRefreshToken: String) {
        this.refreshToken = updateRefreshToken
    }

    fun update(updateRequest: UserUpdateRequest) {
        this.email = updateRequest.email
        this.nickname = updateRequest.nickname
        this.age = updateRequest.age
        this.city = updateRequest.city
    }

    fun updateOAuth(updateRequest: OAuth2UserUpdateRequest) {
        age = updateRequest.age
        city = updateRequest.city
        this.role = UserRole.USER
    }

    fun registerShop(shop: Shop) {
        if (shop.businessRegistrationNumber != this.businessRegistrationNumber) {
            throw IllegalArgumentException("사업자 번호가 일치하지 않습니다.")
        }
        this.myShop = shop
        this.role = UserRole.SELLER_AUTHENTICATION_DONE
    }

    fun changeToSeller(bizNumber: String) {
        businessRegistrationNumber = bizNumber
        userType = UserType.SELLER
        role = UserRole.SELLER_AUTHENTICATION_REQUIRED
    }

    fun updatePassword(password: String, passwordEncoder: PasswordEncoder) {
        this.password = passwordEncoder.encode(password)
    }

    companion object {
        fun fixture(): User {
            return User(
                email = "fixture@naver.com",
                password = "password",
                nickname = "nickname",
                age = 26,
                city = "city",
                businessRegistrationNumber = "none",
            )
        }
    }
}