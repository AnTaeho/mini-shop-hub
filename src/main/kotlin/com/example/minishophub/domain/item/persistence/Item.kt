package com.example.minishophub.domain.item.persistence

import com.example.minishophub.domain.shop.persistence.Shop
import jakarta.persistence.*

@Entity
class Item (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    var id: Long = 0L,

    val name: String,
    val image: String,
    val category: Category,
    var quantity: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    var shop: Shop,

    ) {
    fun sell(count: Int) {
        if (quantity - count < 0) {
            throw IllegalArgumentException("수량이 부족합니다.")
        }
        this.quantity -= count
    }

    fun reStock(count: Int) {
        this.quantity += count
    }
}