package com.example.minishophub.domain.notification.contorller

import com.example.minishophub.domain.notification.persistence.Notification
import com.example.minishophub.domain.notification.service.NotificationService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notice")
class NotificationController(
    private val notificationService: NotificationService,
) {

    @GetMapping("/my")
    fun findMyNotification(@AuthenticationPrincipal userDetails: UserDetails): MutableList<Notification> {
        val email = userDetails.username
        return notificationService.findMyNotification(email)
    }

    @PutMapping("/readAll")
    fun readAll(@AuthenticationPrincipal userDetails: UserDetails) = notificationService.readAll(userDetails.username)

}