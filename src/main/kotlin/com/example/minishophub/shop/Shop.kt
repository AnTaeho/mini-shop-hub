package com.example.minishophub.shop

import com.example.minishophub.user.persistence.User
import jakarta.persistence.*

@Entity
class Shop(

    val name: String,
    val location: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    val owner: User,

    @OneToMany(mappedBy = "shop", cascade = [CascadeType.ALL], orphanRemoval = true)
    val itemList: MutableList<Item> = mutableListOf(),

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    var id: Long? = null,
)