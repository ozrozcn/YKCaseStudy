package com.example.yk

import android.content.SharedPreferences
import com.example.yk.common.Navigator
import com.example.yk.domain.interactor.SignInUseCase
import io.mockk.verify
import org.junit.Test
import io.mockk.mockk
import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.yk.domain.interactor.DeleteUser
import com.example.yk.domain.interactor.GetUserDetails
import com.example.yk.ui.profile.ProfileViewModel

class ProfileViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var getUserDetailUseCase = mockk<GetUserDetails>()

    private var deleteUserUseCase = mockk<DeleteUser>()

    private var sharedPref = mockk<SharedPreferences>()

    private var navigator = mockk<Navigator>()

    @Test
    fun fetchSignInUseCase() {
        ProfileViewModel(getUserDetailUseCase, deleteUserUseCase, navigator, sharedPref)
        verify(exactly = 1) {
            getUserDetailUseCase.execute(eq("JohnDoe@gmail.com"))
        }

    }
}