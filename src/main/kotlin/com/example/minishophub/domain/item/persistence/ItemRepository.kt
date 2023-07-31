package com.example.minishophub.domain.item.persistence

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ItemRepository : JpaRepository<Item, Long> {

    fun findByCategory(category: Category): MutableList<Item>

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select i from Item i where i.id = : itemId")
    fun findItemWithLock(@Param("itemId") itemId: Long): Item?

}