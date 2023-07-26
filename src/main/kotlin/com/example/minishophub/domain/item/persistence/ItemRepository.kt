package com.example.minishophub.domain.item.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<Item, Long> {

    fun findByCategory(category: Category): MutableList<Item>

}