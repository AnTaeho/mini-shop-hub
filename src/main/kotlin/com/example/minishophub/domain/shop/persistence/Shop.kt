package com.example.minishophub.domain.shop.persistence

import com.example.minishophub.domain.base.BaseEntity
import com.example.minishophub.domain.item.persistence.Item
import jakarta.persistence.*

@Entity
class Shop(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    var id: Long = 0,

    val name: String,
    val location: String,
    val businessRegistrationNumber: String,

    var ownerId: Long,

    private var followCount: Long = 0L,

    @OneToMany(mappedBy = "shop", cascade = [CascadeType.ALL], orphanRemoval = true)
    val itemList: MutableList<Item> = mutableListOf(),
) : BaseEntity() {

    fun addItem(item: Item) {
        this.itemList.add(item)
    }

    fun followerIncrease() {
        followCount++
    }

    fun followerDecrease() {
        followCount--
    }

    companion object {
        fun defaultShop(ownerId: Long): Shop {
            return Shop(
                name = "no-name",
                location = "no-location",
                businessRegistrationNumber = "no-number",
                ownerId = ownerId
            )
        }
    }

}