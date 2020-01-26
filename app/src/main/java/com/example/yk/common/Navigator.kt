package com.example.yk.common

import android.content.Context
import android.content.Intent
import com.example.yk.ui.login.LoginActivity
import com.example.yk.ui.profile.ProfileActivity
import com.example.yk.ui.register.UserRegisterActivity
import com.example.yk.ui.splash.SplashActivity

class Navigator(private val context: Context) {

    private fun startActivity(intent: Intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun openRegister() {
        val intent = Intent(context, UserRegisterActivity::class.java)
        startActivity(intent)
    }

    fun openLogin(email: String?) {
        val intent = Intent(context, LoginActivity::class.java)
        intent.putExtra(LoginActivity.ARG_EMAIL, email)
        startActivity(intent)
    }

    fun openPfofile(email: String) {
        val intent = Intent(context, ProfileActivity::class.java)
        intent.putExtra(ProfileActivity.ARG_EMAIL, email)
        startActivity(intent)
    }

    fun openSplash() {
        val intent = Intent(context, SplashActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}