package com.example.minishophub.global.config
//
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.mail.javamail.JavaMailSender
//import org.springframework.mail.javamail.JavaMailSenderImpl
//import java.util.*
//
//@Configuration
//class MailConfig(
//    @Value("\${spring.mail.host}")
//    private var mailHost: String,
//
//    @Value("\${spring.mail.username}")
//    private val mailUserName: String,
//
//    @Value("\${spring.mail.password}")
//    private val mailPassword: String,
//
//    @Value("\${spring.mail.port}")
//    val accessHeader: Int,
//) {
//
//    @Bean
//    fun javaMailSender(): JavaMailSender {
//        val javaMailSender = JavaMailSenderImpl()
//        javaMailSender.host = mailHost
//        javaMailSender.username = mailUserName
//        javaMailSender.password = mailPassword
//
//        javaMailSender.port = accessHeader
//
//        javaMailSender.javaMailProperties = getMailProperties()
//
//        return javaMailSender
//    }
//
//    private fun getMailProperties(): Properties {
//        val properties = Properties()
//        properties.setProperty("mail.transport.protocol", "smtp")
//        properties.setProperty("mail.smtp.auth", "true")
//        properties.setProperty("mail.smtp.starttls.enable", "true")
//        properties.setProperty("mail.debug", "true")
//        properties.setProperty("mail.smtp.ssl.trust","smtp.naver.com")
//        properties.setProperty("mail.smtp.ssl.enable","true")
//        return properties
//    }
//}