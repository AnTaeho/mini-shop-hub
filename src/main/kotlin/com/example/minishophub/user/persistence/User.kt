package com.example.minishophub.user.persistence

import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "USERS")
class User(
    private val email: String,
    private var password: String,
    private val nickname: String,
    private val imageUrl: String? = null,
    private val age: Int,
    private val city: String,

    @Enumerated(EnumType.STRING)
    private var role: UserRole,

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
}