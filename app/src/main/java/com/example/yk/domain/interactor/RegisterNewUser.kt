package com.example.yk.domain.interactor

import com.example.yk.data.model.User
import com.example.yk.data.repository.UserRepository

class RegisterNewUser(private val userRepository: UserRepository) {

    suspend fun execute(user: User, pass: String) {
        return userRepository.registerNewUser(user, pass)
    }
}