package com.example.minishophub.global.evnet

import com.example.minishophub.domain.notification.service.NotificationService
import org.springframework.stereotype.Component

@Component
class NoticeEventListener(
    private val notificationService: NotificationService,
) {

    fun sendNotification(event: NoticeEvent) {
        // 대충 회원 리스트 꺼내서 메세지 담아서 알람 추가
    }
}