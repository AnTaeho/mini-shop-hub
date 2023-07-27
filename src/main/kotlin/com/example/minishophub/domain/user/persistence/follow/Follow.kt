package com.example.minishophub.domain.user.persistence.follow

import com.example.minishophub.domain.shop.persistence.Shop
import com.example.minishophub.domain.user.persistence.user.User
import jakarta.persistence.*

@Entity
class Follow (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    var id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower")
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followedShop")
    var shop: Shop,
)