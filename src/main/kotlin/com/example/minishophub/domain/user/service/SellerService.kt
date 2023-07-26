package com.example.minishophub.domain.user.service

import com.example.minishophub.domain.shop.persistence.Shop
import com.example.minishophub.domain.user.controller.dto.request.SellerApplyRequest
import com.example.minishophub.domain.user.controller.dto.request.UserUpdateRequest
import com.example.minishophub.domain.user.persistence.seller.Seller
import com.example.minishophub.domain.user.persistence.seller.SellerRepository
import com.example.minishophub.domain.user.persistence.buyer.BuyerRepository
import com.example.minishophub.global.util.fail
import com.example.minishophub.global.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SellerService(
    private val sellerRepository: SellerRepository,
    private val buyerRepository: BuyerRepository,
) {

    @Transactional
    fun changeToSeller(email: String, applyRequest: SellerApplyRequest): Seller {
        val owner = buyerRepository.findByEmail(email) ?: fail()

        val seller = Seller(
            email = owner.email,
            password = owner.password,
            nickname = owner.nickname,
            age = owner.age,
            city = owner.city,
            socialId = owner.socialId,
            socialType = owner.socialType,
            refreshToken = owner.refreshToken,
            businessRegistrationNumber = applyRequest.businessRegistrationNumber,
            myShop = Shop.defaultShop(owner.id)
        )

        return sellerRepository.save(seller)
    }

    fun makeCoupon(): String = "쿠폰 발급"

    fun find(sellerId: Long): Seller = sellerRepository.findById(sellerId).get()

    // TODO : 중복적인 유효성 검사 추상화 하기
    @Transactional
    fun update(sellerId: Long, updateRequest: UserUpdateRequest) {
        val seller = sellerRepository.findByIdOrThrow(sellerId)

        if (sellerRepository.findByEmail(updateRequest.email) != null) {
            throw IllegalArgumentException("이미 존재하는 이메일 입니다.")
        }
        if (sellerRepository.findByNickname(updateRequest.nickname) != null) {
            throw IllegalArgumentException("이미 존재하는 닉네임 입니다.")
        }

        seller.update(updateRequest)

    }

    fun deleteSeller(sellerId: Long) = sellerRepository.deleteById(sellerId)

}