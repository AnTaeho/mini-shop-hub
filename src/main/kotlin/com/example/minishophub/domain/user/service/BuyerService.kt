package com.example.minishophub.domain.user.service

import com.example.minishophub.domain.user.controller.dto.request.OAuth2UserUpdateRequest
import com.example.minishophub.domain.user.controller.dto.request.UserJoinRequest
import com.example.minishophub.domain.user.controller.dto.request.UserUpdateRequest
import com.example.minishophub.domain.user.persistence.buyer.Buyer
import com.example.minishophub.domain.user.persistence.buyer.BuyerRepository
import com.example.minishophub.domain.user.persistence.UserRole
import com.example.minishophub.global.util.findByIdOrThrow
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BuyerService (
    private val buyerRepository: BuyerRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    @Transactional
    fun join(joinRequest: UserJoinRequest) {

        checkEmail(joinRequest.email)
        checkNickname(joinRequest.nickname)

        val buyer = Buyer(
            email = joinRequest.email,
            password = joinRequest.password,
            nickname = joinRequest.nickname,
            age = joinRequest.age,
            city = joinRequest.city,
            role = UserRole.USER,
        )

        buyer.passwordEncode(passwordEncoder)
        buyerRepository.save(buyer)
    }

    fun find(userId: Long): Buyer {
        return buyerRepository.findByIdOrThrow(userId)
    }

    @Transactional
    fun update(userId: Long, updateRequest: UserUpdateRequest) {
        val user = buyerRepository.findByIdOrThrow(userId)

        checkEmail(updateRequest.email)
        checkNickname(updateRequest.nickname)

        user.update(updateRequest)
    }

    @Transactional
    fun delete(userId: Long) {
        buyerRepository.deleteById(userId)
    }

    @Transactional
    fun updateOAuth2(updateRequest: OAuth2UserUpdateRequest, email: String) {
        val user = buyerRepository.findByEmail(email)!!
        user.updateOAuth(updateRequest)
    }

    private fun checkEmail(email: String) {
        if (buyerRepository.existsByEmail(email)) {
            throw IllegalArgumentException("이미 존재하는 이메일 입니다.")
        }
    }

    private fun checkNickname(nickname: String) {
        if (buyerRepository.existsByNickname(nickname)) {
            throw IllegalArgumentException("이미 존재하는 닉네임 입니다.")
        }
    }


}
