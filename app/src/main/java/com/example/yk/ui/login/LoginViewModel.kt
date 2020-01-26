package com.example.yk.ui.login

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yk.common.Navigator

import com.example.yk.domain.interactor.SignInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val signInUseCase: SignInUseCase,
    private val sharedPref: SharedPreferences,
    private val navigator: Navigator
) : ViewModel() {

    val finishActivity = MutableLiveData<Unit>()
    val alertObservable = MutableLiveData<String>()

    /**
     * Login with User Credidentials
     */
    fun login(email: String, password: String, rememberMe: Boolean = false) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    signInUseCase.execute(email, password)
                }
                if (rememberMe) {
                    sharedPref.edit().putString("USER_MAIL", email).apply()
                } else {
                    sharedPref.edit().remove("USER_MAIL").apply()
                }
                navigator.openPfofile(email)
                finishActivity.postValue(null)

            } catch (e: Exception) {
                alertObservable.postValue(e.message)
            }
        }
    }
}
