package com.example.yk.di

import android.content.Context
import android.content.SharedPreferences
import com.example.yk.common.Navigator
import com.example.yk.data.firebase.FirebaseRepository
import com.example.yk.data.repository.UserRepository
import com.example.yk.domain.interactor.DeleteUser
import com.example.yk.domain.interactor.GetUserDetails
import com.example.yk.domain.interactor.RegisterNewUser
import com.example.yk.domain.interactor.SignInUseCase
import com.example.yk.ui.login.LoginViewModel
import com.example.yk.ui.profile.ProfileViewModel
import com.example.yk.ui.register.UserRegisterViewModel
import com.example.yk.ui.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val applicationModule: Module = module {

    single { FirebaseRepository() as UserRepository }
    factory { GetUserDetails(userRepository = get()) }
    factory { RegisterNewUser(userRepository = get()) }
    factory { SignInUseCase(userRepository = get()) }
    factory { DeleteUser(userRepository = get()) }
    single { Navigator(androidContext()) }
}

val splashModule: Module = module {
    viewModel {
        SplashViewModel(
            navigator = get(),
            sharedPref = provideSharedPref(androidContext())
        )
    }
}

val registerModule: Module = module {
    viewModel { UserRegisterViewModel(registerNewUser = get(), navigator = get()) }
}

val signInModule: Module = module {
    viewModel {
        LoginViewModel(
            signInUseCase = get(),
            sharedPref = provideSharedPref(androidContext()),
            navigator = get()
        )
    }
}

val profileModule: Module = module {
    viewModel {
        ProfileViewModel(
            userDetails = get(),
            deleteUser = get(),
            navigator = get(),
            sharedPref = provideSharedPref(androidContext())
        )
    }
}

val moduleList =
    listOf(applicationModule, signInModule, splashModule, registerModule, profileModule)

private const val PREFS_FILE_KEY = "com.example.yk"

private fun provideSharedPref(context: Context): SharedPreferences =
    context.getSharedPreferences(PREFS_FILE_KEY, Context.MODE_PRIVATE)
