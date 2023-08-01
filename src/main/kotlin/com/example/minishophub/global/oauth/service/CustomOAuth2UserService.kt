package com.example.minishophub.global.oauth.service

import com.example.minishophub.domain.user.persistence.SocialType
import com.example.minishophub.domain.user.persistence.user.User
import com.example.minishophub.domain.user.persistence.user.UserRepository
import com.example.minishophub.global.oauth.CustomOAuth2User
import com.example.minishophub.global.oauth.OAuthAttributes
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.Collections

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    companion object {
        const val NAVER = "naver"
        const val KAKAO = "kakao"
        const val GOOGLE = "google"
    }

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {

        val delegate: OAuth2UserService<OAuth2UserRequest, OAuth2User> = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)
        val registrationId = userRequest.clientRegistration.registrationId
        val socialType = getSocialType(registrationId)
        val userNameAttributeName = getUserNameAttribute(userRequest)
        val attributes = oAuth2User.attributes
        val extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes)
        val createUser: User = getUser(extractAttributes, socialType)

        return CustomOAuth2User(
            Collections.singleton(SimpleGrantedAuthority(createUser.role.key)),
            attributes,
            extractAttributes.nameAttributeKey,
            createUser.email,
            createUser.role
        )
    }

    private fun getUserNameAttribute(userRequest: OAuth2UserRequest): String =
        userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName

    private fun getSocialType(registrationId: String): SocialType {
        return when (registrationId) {
            NAVER -> SocialType.NAVER
            KAKAO -> SocialType.KAKAO
            GOOGLE -> SocialType.GOOGLE
            else -> SocialType.NO_SOCIAL
        }
    }

    private fun getUser(attributes: OAuthAttributes, socialType: SocialType): User {
        return userRepository.findBySocialTypeAndSocialId(
            socialType,
            attributes.oAuth2UserInfo.getId()!!
        ) ?: return saveUser(attributes, socialType)
    }

    private fun saveUser(attributes: OAuthAttributes, socialType: SocialType): User {
        val createUser = attributes.toEntity(socialType, attributes.oAuth2UserInfo)
        return userRepository.save(createUser)
    }

}