package com.example.minishophub.domain.shop.persistence

import com.example.minishophub.domain.base.BaseEntity
import com.example.minishophub.domain.item.persistence.Item
import com.example.minishophub.domain.user.persistence.User
import jakarta.persistence.*

@Entity
class Shop(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    var id: Long = 0,

    val name: String,
    val location: String,
    val businessRegistrationNumber: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    val owner: User,

    @OneToMany(mappedBy = "shop", cascade = [CascadeType.ALL], orphanRemoval = true)
    val itemList: MutableList<Item> = mutableListOf(),

    ) : BaseEntity()