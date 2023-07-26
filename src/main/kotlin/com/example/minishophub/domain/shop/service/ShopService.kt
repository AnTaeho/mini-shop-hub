package com.example.minishophub.domain.shop.service

import com.example.minishophub.domain.shop.controller.dto.request.ShopRegisterRequest
import com.example.minishophub.domain.shop.persistence.Shop
import com.example.minishophub.domain.shop.persistence.ShopRepository
import com.example.minishophub.domain.user.persistence.seller.SellerRepository
import com.example.minishophub.global.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ShopService(
    private val shopRepository: ShopRepository,
    private val sellerRepository: SellerRepository,
) {

    @Transactional
    fun registerShop(shopRegisterRequest: ShopRegisterRequest, ownerId: Long): Shop {
        //TODO AccessToken 을 이용한 유저 탐색으로 변경 예정
        val owner = sellerRepository.findById(ownerId).get()
        val shop = Shop(
            name = shopRegisterRequest.name,
            location = shopRegisterRequest.location,
            businessRegistrationNumber = shopRegisterRequest.bizNumber,
            ownerId = owner.id
        )
        owner.registerShop(shop)
        return shopRepository.save(shop)
    }

    fun findShop(shopId: Long): Shop
            = shopRepository.findById(shopId).get()

    fun findShop(bizNumber: String): Shop
        = shopRepository.findByBusinessRegistrationNumber(bizNumber) ?: fail()

    @Transactional
    fun deleteShop(businessRegistrationNumber: String) {
        val shop =
            shopRepository.findByBusinessRegistrationNumber(businessRegistrationNumber) ?: fail()
        shopRepository.delete(shop)
    }
}