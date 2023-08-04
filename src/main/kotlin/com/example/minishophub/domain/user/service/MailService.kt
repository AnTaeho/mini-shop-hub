package com.example.minishophub.domain.user.service

import com.example.minishophub.domain.user.controller.dto.request.MailAuthRequest
import com.example.minishophub.domain.user.controller.dto.request.MailRequest
import com.example.minishophub.global.jwt.service.JwtService
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService(
    private val javaMailSender: JavaMailSender,
    private val jwtService: JwtService,
) {

    fun sendResettingPasswordMail(request: MailRequest) {
        val message = SimpleMailMessage()
        message.setTo(request.email)
        message.subject = request.title
        message.text = request.content + request.email
        javaMailSender.send(message)
    }

    fun sendAuthMail(request: MailAuthRequest) {
        val message = SimpleMailMessage()
        val accessToken = jwtService.createAccessToken(request.email)
        message.setTo(request.email)
        message.subject = request.title
        message.text = request.content + accessToken
        javaMailSender.send(message)
    }
}