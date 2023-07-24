package com.example.minishophub.domain.user.persistence

import com.example.minishophub.domain.user.controller.dto.request.UserUpdateRequest
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "USERS")
class User(
    var email: String,
    var password: String,
    var nickname: String,
    private val imageUrl: String? = null,
    var age: Int,
    var city: String,

    @Enumerated(EnumType.STRING)
    var role: UserRole,

    @Enumerated(EnumType.STRING)
    private val socialType: SocialType? = null,

    private val socialId: String? = null,
    private var refreshToken: String? = null,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long? = null,
) {

    fun authorizeUser() {
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

    companion object {
        fun fixture(): User {
            return User(
                email = "fixture@naver.com",
                password = "password",
                nickname = "nickname",
                age = 26,
                city = "city",
                role = UserRole.GUEST,
            )
        }
    }
}