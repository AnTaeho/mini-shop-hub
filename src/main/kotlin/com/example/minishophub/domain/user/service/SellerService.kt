package com.example.minishophub.domain.user.service

import com.example.minishophub.domain.user.controller.dto.request.SellerApplyRequest
import com.example.minishophub.domain.user.controller.dto.request.UserUpdateRequest
import com.example.minishophub.domain.user.persistence.UserRole
import com.example.minishophub.domain.user.persistence.user.User
import com.example.minishophub.domain.user.persistence.user.UserRepository
import com.example.minishophub.global.util.fail
import com.example.minishophub.global.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SellerService(
    private val userRepository: UserRepository,
) {

    @Transactional
    fun changeToSeller(email: String, applyRequest: SellerApplyRequest): User {
        val owner = userRepository.findByEmail(email) ?: fail()

        if (owner.role != UserRole.USER) {
            throw IllegalArgumentException("추가 인증이 안되어 있는 유저 입니다.")
        }

        owner.changeToSeller(applyRequest.bizNumber)

        return userRepository.save(owner)
    }

    @Transactional
    fun update(sellerId: Long, updateRequest: UserUpdateRequest) {
        val seller = userRepository.findByIdOrThrow(sellerId)

        checkEmail(updateRequest.email)
        checkNickname(updateRequest.nickname)

        seller.update(updateRequest)

    }

    fun find(userId: Long) : User = userRepository.findByIdOrThrow(userId)

    private fun checkEmail(email: String) {
        if (userRepository.findEmail(email) != null) {
            throw IllegalArgumentException("이미 존재하는 이메일 입니다.")
        }
    }

    private fun checkNickname(nickname: String) {
        if (userRepository.findNickname(nickname) != null) {
            throw IllegalArgumentException("이미 존재하는 닉네임 입니다.")
        }
    }

    fun deleteSeller(sellerId: Long) = userRepository.deleteById(sellerId)

}