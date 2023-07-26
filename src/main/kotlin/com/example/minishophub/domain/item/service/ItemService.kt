package com.example.minishophub.domain.item.service

import com.example.minishophub.domain.item.controller.dto.request.ItemRegisterRequest
import com.example.minishophub.domain.item.persistence.Category
import com.example.minishophub.domain.item.persistence.Item
import com.example.minishophub.domain.item.persistence.ItemRepository
import com.example.minishophub.domain.shop.persistence.ShopRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemService(
    private val itemRepository: ItemRepository,
    private val shopRepository: ShopRepository,
) {

    @Transactional
    fun registerItem(registerRequest: ItemRegisterRequest, shopId: Long): Item {
        val shop = shopRepository.findById(shopId).get()
        val item = Item(
            name = registerRequest.name,
            image = registerRequest.image,
            category = registerRequest.category,
            quantity = registerRequest.quantity,
            shop = shop
        )
        shop.addItem(item)
        return itemRepository.save(item)
    }

    @Transactional
    fun sellItem(itemId: Long, count: Int = 1) {
        val item = itemRepository.findById(itemId).get()
        item.sell(count)
    }

    @Transactional
    fun reStockItem(itemId: Long, count: Int = 1) {
        val item = itemRepository.findById(itemId).get()
        item.reStock(count)
    }

    fun searchCategory(category: Category): MutableList<Item> = itemRepository.findByCategory(category)

    fun findItem(itemId: Long): Item = itemRepository.findById(itemId).get()

    @Transactional
    fun deleteItem(itemId: Long) = itemRepository.deleteById(itemId)

}