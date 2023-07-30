package com.example.minishophub.domain.user.service

import com.example.minishophub.domain.user.controller.dto.request.MailRequest
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService(
    private val javaMailSender: JavaMailSender,
) {

    fun sendResettingPasswordMail(request: MailRequest) {
        val message = SimpleMailMessage()
        message.setTo(request.email)
        message.subject = request.title
        message.text = request.content
        javaMailSender.send(message)

    }
}