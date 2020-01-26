package com.example.yk.ui.login

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.yk.R
import com.example.yk.base.BaseActivity
import com.example.yk.databinding.ActivityLoginBinding
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding

    private val email by lazy { intent.getStringExtra(ARG_EMAIL) }

    companion object {
        const val ARG_EMAIL = "arg_email"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBanding()
        initUI()
        initLiveData()
    }

    private fun initLiveData() {
        viewModel.alertObservable.observe(this, Observer {
            showDialog(it)
        })

        viewModel.finishActivity.observe(this, Observer {
            finish()
        })
    }

    private fun initUI() {
        binding.apply {
            login.setOnClickListener {
                viewModel?.login(
                    email.text.toString(),
                    password.text.toString(),
                    rememberMe.isChecked
                )
            }

            this@LoginActivity.email?.apply {
                email.setText(this)
                rememberMe.isChecked = true
            }
        }
    }

    private fun initBanding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
    }
}
