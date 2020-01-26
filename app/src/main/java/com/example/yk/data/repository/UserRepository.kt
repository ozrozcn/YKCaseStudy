package com.example.yk.data.repository

import com.example.yk.data.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun registerNewUser(user: User, pass: String)

    suspend fun signIn(email: String, password: String)

    suspend fun deleteUser(email: String)

    @ExperimentalCoroutinesApi
    fun getUserDetail(email: String): Flow<User>
}