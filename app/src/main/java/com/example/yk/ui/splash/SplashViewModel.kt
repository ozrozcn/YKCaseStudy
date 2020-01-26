package com.example.yk.ui.splash

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yk.common.Navigator

class SplashViewModel(
    private val navigator: Navigator,
    sharedPref: SharedPreferences
) : ViewModel() {

    internal val finishActivity = MutableLiveData<Unit?>()

    init {
        val userMail = sharedPref.getString("USER_MAIL", null)
        if (userMail != null) {
            openSignIn(userMail)
        }
    }

    fun openRegister() {
        navigator.openRegister()
    }

    fun openSignIn(mail: String?) {
        navigator.openLogin(mail)
    }
}
