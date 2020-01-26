package com.example.yk.ui.register

import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yk.common.Navigator
import com.example.yk.data.model.User
import com.example.yk.domain.interactor.RegisterNewUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class UserRegisterViewModel(
    private val registerNewUser: RegisterNewUser,
    private val navigator: Navigator
) : ViewModel() {

    val userImageObservable = ObservableField<Uri>()
    val alertLiveData = MutableLiveData<String>()
    internal val finishActivity = MutableLiveData<Unit?>()

    /**
     * Register new user and save to remote db
     */
    fun registerUser(email: String, password: String, name: String, surname: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    registerNewUser.execute(
                        User(mail = email, name = name, surname = surname, photoUri = userImageObservable.get().toString()),
                        password
                    )
                }
                navigator.openLogin(email)
                finishActivity.postValue(Unit)
            } catch (e: Exception) {
                alertLiveData.postValue(e.message)
            }
        }
    }
}