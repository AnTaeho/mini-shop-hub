package com.example.minishophub.global.oauth

import com.example.minishophub.domain.user.persistence.UserRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User

class CustomOAuth2User(
    authorities: MutableCollection<out GrantedAuthority>?,
    attributes: MutableMap<String, Any>?,
    nameAttributeKey: String?,
    val email: String,
    val role: UserRole,
) : DefaultOAuth2User(authorities, attributes, nameAttributeKey)