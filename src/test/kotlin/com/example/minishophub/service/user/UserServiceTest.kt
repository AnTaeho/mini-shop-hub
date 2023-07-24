package com.example.minishophub.service.user

import com.example.minishophub.user.controller.dto.request.UserJoinRequest
import com.example.minishophub.user.controller.dto.request.UserUpdateRequest
import com.example.minishophub.user.persistence.User
import com.example.minishophub.user.persistence.UserRepository
import com.example.minishophub.user.service.UserService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userService: UserService,
    private val userRepository: UserRepository,
) {

    @AfterEach
    fun clear() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("회원 가입 성공")
    fun joinTest() {
        //given
        val joinRequest = UserJoinRequest(
            "test@naver.com",
            "test123",
            "test1",
            26,
            "seoul"
        )

        //when
        userService.join(joinRequest)

        //then
        val result = userRepository.findAll()
        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].email).isEqualTo("test@naver.com")
        assertThat(result[0].nickname).isEqualTo("test1")
        assertThat(result[0].age).isEqualTo(26)
        assertThat(result[0].city).isEqualTo("seoul")
    }

    @Test
    @DisplayName("회원 가입 실패 - 이메일 중복")
    fun joinFailTest1() {
        //given
        val joinRequest = UserJoinRequest(
            "test@naver.com",
            "test123",
            "test1",
            26,
            "seoul"
        )
        userService.join(joinRequest)

        //when & then
        val joinRequest2 = UserJoinRequest(
            "test@naver.com",
            "test123",
            "test2",
            26,
            "seoul"
        )

        val message = assertThrows<IllegalArgumentException> {
            userService.join(joinRequest2)
        }.message

        assertThat(message).isEqualTo("이미 존재하는 이메일 입니다.")
    }

    @Test
    @DisplayName("회원 가입 실패 - 닉네임 중복")
    fun joinFailTest2() {
        //given
        val joinRequest = UserJoinRequest(
            "test@naver.com",
            "test123",
            "test1",
            26,
            "seoul"
        )
        userService.join(joinRequest)

        //when & then
        val joinRequest2 = UserJoinRequest(
            "test2@naver.com",
            "test123",
            "test1",
            26,
            "seoul"
        )

        val message = assertThrows<IllegalArgumentException> {
            userService.join(joinRequest2)
        }.message

        assertThat(message).isEqualTo("이미 존재하는 닉네임 입니다.")
    }

    @Test
    @DisplayName("유저 조회 성공")
    fun findUserTest() {
        //given
        val user = User.fixture()
        val savedUser = userRepository.save(user)

        //when
        val findUser = userService.find(savedUser.id!!)

        //then
        assertThat(savedUser.email).isEqualTo(findUser.email)
        assertThat(savedUser.nickname).isEqualTo(findUser.nickname)
        assertThat(savedUser.age).isEqualTo(findUser.age)
        assertThat(savedUser.city).isEqualTo(findUser.city)
        assertThat(savedUser.password).isEqualTo(findUser.password)
    }

    @Test
    @DisplayName("유저 조회 실패")
    fun findFailTest() {
        //given
        val user = User.fixture()
        val savedUser = userRepository.save(user)

        //when & then
        val message = assertThrows<IllegalArgumentException> {
            userService.find(savedUser.id!!+1L)
        }.message
        assertThat(message).isEqualTo("존재하지 않는 유저 입니다.")
    }

    @Test
    @DisplayName("유저 수정 성공")
    fun updateTest() {
        //given
        val user = User.fixture()
        val savedUser = userRepository.save(user)

        val updateRequest = UserUpdateRequest(
            email = "update@naver.com",
            nickname = "update",
            age = 24,
            city = "seoul"
        )

        //when
        val id = savedUser.id!!
        userService.update(id, updateRequest)

        //then
        val findUser = userService.find(id)
        assertThat(findUser.email).isEqualTo("update@naver.com")
        assertThat(findUser.nickname).isEqualTo("update")
        assertThat(findUser.age).isEqualTo(24)
        assertThat(findUser.city).isEqualTo("seoul")
    }

    @Test
    @DisplayName("회원 수정 실패 - 닉네임 중복")
    fun updateFailTest1() {
        //given
        val user = User.fixture()
        val savedUser = userRepository.save(user)

        val updateRequest = UserUpdateRequest(
            email = "update@naver.com",
            nickname = "nickname",
            age = 24,
            city = "seoul"
        )

        //when & then

        val message = assertThrows<IllegalArgumentException> {
            userService.update(savedUser.id!!, updateRequest)
        }.message

        assertThat(message).isEqualTo("이미 존재하는 닉네임 입니다.")
    }

    @Test
    @DisplayName("회원 수정 실패 - 이메일 중복")
    fun updateFailTest2() {
        //given
        val user = User.fixture()
        val savedUser = userRepository.save(user)

        val updateRequest = UserUpdateRequest(
            email = "fixture@naver.com",
            nickname = "fixture",
            age = 24,
            city = "seoul"
        )

        //when & then

        val message = assertThrows<IllegalArgumentException> {
            userService.update(savedUser.id!!, updateRequest)
        }.message

        assertThat(message).isEqualTo("이미 존재하는 이메일 입니다.")
    }

    @Test
    @DisplayName("회원 삭제")
    fun deleteUserTest() {
        //given
        val savedUser = userRepository.save(User.fixture())

        //when
        userService.delete(savedUser.id!!)

        //then
        val result = userRepository.findAll()
        assertThat(result).isEmpty()
    }

}