package com.example.yk.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.yk.R
import com.example.yk.databinding.ActivityProfileBinding
import com.example.yk.ui.login.LoginActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileActivity : AppCompatActivity() {

    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var binding: ActivityProfileBinding

    private val email by lazy { intent.getStringExtra(LoginActivity.ARG_EMAIL) }

    companion object {
        const val LAYOUT_RES = R.layout.activity_profile
        const val ARG_EMAIL = "arg_email"
    }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBanding()
        initUI()
    }

    @ExperimentalCoroutinesApi
    private fun initUI() {
        viewModel.getUserDetails(email)

        binding.apply {
            deleteAccount.setOnClickListener {
                viewModel?.deleteUser(this@ProfileActivity.email)
            }
        }
    }

    private fun initBanding() {
        binding = DataBindingUtil.setContentView(this, LAYOUT_RES)
        binding.viewModel = viewModel
    }
}