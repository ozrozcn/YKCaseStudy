package com.example.yk.domain.interactor

import com.example.yk.data.model.User
import com.example.yk.data.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

open class GetUserDetails(private val userRepository: UserRepository) {

    @ExperimentalCoroutinesApi
    fun execute(email: String): Flow<User?> {
        return userRepository.getUserDetail(email)
    }
}