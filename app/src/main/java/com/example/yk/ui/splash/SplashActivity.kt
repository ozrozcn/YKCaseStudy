package com.example.yk.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.example.yk.R
import com.example.yk.databinding.ActivitySplashBinding
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBanding()
        initLiveData()
        initUI()
    }

    private fun initUI() {
        binding.apply {
            viewModel?.let { vm ->
                signIn.setOnClickListener {
                    vm.openSignIn(null)
                }

                signUp.setOnClickListener {
                    vm.openRegister()
                }
            }
        }
    }

    private fun initBanding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.viewModel = viewModel
    }

    private fun initLiveData() {
        viewModel.finishActivity.observe(this, Observer {
            finish()
        })
    }
}