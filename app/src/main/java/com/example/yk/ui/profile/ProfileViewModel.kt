package com.example.yk.ui.profile

import android.content.SharedPreferences
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.yk.common.Navigator
import com.example.yk.data.model.User
import com.example.yk.domain.interactor.DeleteUser
import com.example.yk.domain.interactor.GetUserDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val userDetails: GetUserDetails,
    private val deleteUser: DeleteUser,
    private val navigator: Navigator,
    private val sharedPref: SharedPreferences
) : ViewModel() {

    val userObservable = ObservableField<User>()

    @ExperimentalCoroutinesApi
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getUserDetails(email: String) {
        viewModelScope.launch {
            try {
                userDetails.execute(email)
                    .flowOn(Dispatchers.IO)
                    .collect { user ->
                        userObservable.set(user)
                    }
            } catch (e: Exception) {
            }
        }
    }

    fun deleteUser(email: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    deleteUser.execute(email)
                }
                sharedPref.edit().remove("USER_MAIL").apply()
                navigator.openSplash()

            } catch (e: Exception) {

            }
        }
    }
}
