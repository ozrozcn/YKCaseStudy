package com.example.yk.domain.interactor

import com.example.yk.data.repository.UserRepository

class SignInUseCase(private val userRepository: UserRepository) {

    suspend fun execute(mail: String, pass: String) {
        return userRepository.signIn(mail, pass)
    }
}