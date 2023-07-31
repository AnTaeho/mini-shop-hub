package com.example.minishophub.domain.user.controller

import com.example.minishophub.domain.user.controller.dto.request.SellerApplyRequest
import com.example.minishophub.domain.user.controller.dto.request.UserUpdateRequest
import com.example.minishophub.domain.user.persistence.user.User
import com.example.minishophub.domain.user.service.SellerService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/seller")
class SellerController(
    private val sellerService: SellerService,
) {

    @PostMapping
    fun changeToSeller(@RequestBody applyRequest: SellerApplyRequest,
                       @AuthenticationPrincipal userDetails: UserDetails,
    ): User = sellerService.changeToSeller(userDetails.username!!, applyRequest)


    @GetMapping("/user/{userId}")
    fun findUser(@PathVariable userId: Long): User = sellerService.find(userId)


    @PutMapping("/user/{userId}")
    fun updateUser(@PathVariable userId: Long,
                   @RequestBody updateRequest: UserUpdateRequest
    ) = sellerService.update(userId, updateRequest)

    @DeleteMapping("/{sellerId}")
    fun deleteSeller(@PathVariable sellerId: Long) = sellerService.deleteSeller(sellerId)
}