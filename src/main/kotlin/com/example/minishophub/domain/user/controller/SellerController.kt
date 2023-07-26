package com.example.minishophub.domain.user.controller

import com.example.minishophub.domain.user.controller.dto.request.SellerApplyRequest
import com.example.minishophub.domain.user.controller.dto.request.UserUpdateRequest
import com.example.minishophub.domain.user.controller.dto.response.UserResponse
import com.example.minishophub.domain.user.persistence.seller.Seller
import com.example.minishophub.domain.user.service.SellerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/seller")
class SellerController(
    private val sellerService: SellerService,
) {

    @PostMapping("/{userId}")
    fun changeToSeller(@PathVariable userId: Long,
                       @RequestBody applyRequest: SellerApplyRequest
    ): Seller = sellerService.changeToSeller(userId, applyRequest)


    @GetMapping("/user/{userId}")
    fun findUser(@PathVariable userId: Long): Seller = sellerService.find(userId)


    @PutMapping("/user/{userId}")
    fun updateUser(@PathVariable userId: Long,
                   @RequestBody updateRequest: UserUpdateRequest
    ) = sellerService.update(userId, updateRequest)

    @DeleteMapping("/{sellerId}")
    fun deleteSeller(@PathVariable sellerId: Long) = sellerService.deleteSeller(sellerId)
}