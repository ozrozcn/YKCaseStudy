package com.example.yk

import com.example.yk.data.model.User
import com.example.yk.data.repository.UserRepository
import com.example.yk.domain.interactor.GetUserDetails
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetUserDetailsUseCaseTest {

    private val repo: UserRepository = mockk()

    private val name = "John"
    private val surname = "Doe"
    private val mail = "JohnDoe@gmail.com"

    private val dummyUser = User(name, surname, mail)

    @Before
    fun init() {
        coEvery { repo.getUserDetail(mail) } returns flowOf(dummyUser)
    }

    @Test
    fun userLoaded() = runBlocking {
        // Given
        val useCase = GetUserDetails(repo)

        // When
        val user = useCase.execute(mail).first()

        // Then
        assert(dummyUser == user)
    }
}