package com.example.minishophub.shop

import jakarta.persistence.*

@Entity
class Item (

    val name: String,
    val image: String,
    val category: Category,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    val shop: Shop,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    var id: Long? = null,
)