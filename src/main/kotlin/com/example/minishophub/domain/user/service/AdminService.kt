package com.example.minishophub.domain.user.service

import com.example.minishophub.domain.item.persistence.Item
import com.example.minishophub.domain.item.persistence.ItemRepository
import com.example.minishophub.domain.shop.persistence.Shop
import com.example.minishophub.domain.shop.persistence.ShopRepository
import com.example.minishophub.domain.user.persistence.user.User
import com.example.minishophub.domain.user.persistence.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AdminService(
    private val userRepository: UserRepository,
    private val shopRepository: ShopRepository,
    private val itemRepository: ItemRepository,
) {
    fun findAllUser(): MutableList<User> = userRepository.findAll()

    fun findAllShop(): MutableList<Shop> = shopRepository.findAll()

    fun findAllItem(): MutableList<Item> = itemRepository.findAll()
}