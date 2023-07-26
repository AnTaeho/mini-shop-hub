package com.example.minishophub.domain.user.persistence.buyer

import com.example.minishophub.domain.base.BaseEntity
import com.example.minishophub.domain.user.controller.dto.request.OAuth2UserUpdateRequest
import com.example.minishophub.domain.user.controller.dto.request.UserUpdateRequest
import com.example.minishophub.domain.user.persistence.SocialType
import com.example.minishophub.domain.user.persistence.UserRole
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "BUYER")
class Buyer(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long = 0L,

    var email: String,
    var password: String,
    var nickname: String,
    var age: Int,
    var city: String,
    var socialId: String? = null,
    var refreshToken: String? = null,

    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.GUEST,

    @Enumerated(EnumType.STRING)
    var socialType: SocialType = SocialType.NO_SOCIAL,
) : BaseEntity() {

    private fun authorizeUser() {
        this.role = UserRole.USER
    }

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
        authorizeUser()
    }

    companion object {
        fun fixture(): Buyer {
            return Buyer(
                email = "fixture@naver.com",
                password = "password",
                nickname = "nickname",
                age = 26,
                city = "city",
            )
        }
    }
}