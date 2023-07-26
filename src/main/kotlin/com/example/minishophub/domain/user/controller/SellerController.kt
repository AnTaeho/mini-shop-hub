package com.example.minishophub.domain.user.controller

import com.example.minishophub.domain.user.controller.dto.request.SellerApplyRequest
import com.example.minishophub.domain.user.controller.dto.request.UserUpdateRequest
import com.example.minishophub.domain.user.persistence.seller.Seller
import com.example.minishophub.domain.user.service.SellerService
import com.example.minishophub.global.jwt.service.JwtService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/seller")
class SellerController(
    private val sellerService: SellerService,
    private val jwtService: JwtService,
) {

    @PostMapping
    fun changeToSeller(@RequestBody applyRequest: SellerApplyRequest,
                       request: HttpServletRequest
    ): Seller {
        val accessToken = jwtService.extractAccessToken(request) ?: throw IllegalArgumentException()
        val email = jwtService.extractEmail(accessToken)
        return sellerService.changeToSeller(email!!, applyRequest)
    }


    @GetMapping("/user/{userId}")
    fun findUser(@PathVariable userId: Long): Seller = sellerService.find(userId)


    @PutMapping("/user/{userId}")
    fun updateUser(@PathVariable userId: Long,
                   @RequestBody updateRequest: UserUpdateRequest
    ) = sellerService.update(userId, updateRequest)

    @DeleteMapping("/{sellerId}")
    fun deleteSeller(@PathVariable sellerId: Long) = sellerService.deleteSeller(sellerId)
}