package com.example.minishophub.domain.shop.service

import com.example.minishophub.domain.shop.controller.dto.request.ShopRegisterRequest
import com.example.minishophub.domain.shop.persistence.Shop
import com.example.minishophub.domain.shop.persistence.ShopRepository
import com.example.minishophub.domain.user.persistence.UserRole
import com.example.minishophub.domain.user.persistence.user.User
import com.example.minishophub.domain.user.persistence.user.UserRepository
import com.example.minishophub.global.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ShopService(
    private val shopRepository: ShopRepository,
    private val userRepository: UserRepository,
) {

    @Transactional
    fun registerShop(shopRegisterRequest: ShopRegisterRequest, email: String): Shop {
        val owner = userRepository.findByEmail(email) ?: fail()
        checkSeller(owner)
        val shop = Shop(
            name = shopRegisterRequest.name,
            location = shopRegisterRequest.location,
            businessRegistrationNumber = shopRegisterRequest.bizNumber,
            ownerId = owner.id
        )
        owner.registerShop(shop)
        return shopRepository.save(shop)
    }

    private fun checkSeller(owner: User) {
        if (owner.role != UserRole.SELLER_AUTHENTICATION_REQUIRED) {
            throw IllegalArgumentException("셀러가 아닙니다.")
        }
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