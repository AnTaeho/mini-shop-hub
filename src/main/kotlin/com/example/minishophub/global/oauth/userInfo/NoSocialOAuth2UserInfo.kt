package com.example.minishophub.global.oauth.userInfo

class NoSocialOAuth2UserInfo(attributes: MutableMap<String, Any>) : OAuth2UserInfo(attributes) {
    override fun getId(): String? {
        return null
    }

    override fun getNickname(): String? {
        return null
    }

    override fun getEmail(): String? {
        return null
    }

    override fun getImageUrl(): String? {
        return null
    }
}