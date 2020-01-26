package com.example.yk.domain.interactor

import com.example.yk.data.repository.UserRepository

class DeleteUser(private val userRepository: UserRepository) {

    suspend fun execute(email: String) {
        userRepository.deleteUser(email)
    }
}