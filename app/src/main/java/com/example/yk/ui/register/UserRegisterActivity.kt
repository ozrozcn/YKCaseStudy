package com.example.yk.ui.register

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.yk.R
import com.example.yk.base.BaseActivity
import com.example.yk.common.FilePicker
import com.example.yk.databinding.ActivityRegisterBinding
import org.koin.android.viewmodel.ext.android.viewModel

class UserRegisterActivity : BaseActivity() {

    private val viewModel: UserRegisterViewModel by viewModel()
    private lateinit var binding: ActivityRegisterBinding

    private val filePicker: FilePicker by lazy {
        FilePicker(this)
    }

    companion object {
        const val LAYOUT_RES = R.layout.activity_register
        const val REQ_CODE_TAKE_PICTURE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBanding()
        initUI()
        initLiveData()
    }

    private fun initUI() {
        binding.apply {
            register.setOnClickListener {
                viewModel?.registerUser(
                    email.text.toString(),
                    password.text.toString(),
                    name.text.toString(),
                    surname.text.toString()
                )
            }

            userImage.setOnClickListener {
                clickTakePhoto()
            }
        }
    }

    private fun initLiveData() {
        viewModel.alertLiveData.observe(this, Observer {
            showDialog(it)
        })

        viewModel.finishActivity.observe(this, Observer {
            finish()
        })
    }

    /**
     * Open Camera to capture picture
     */
    private fun clickTakePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(this.packageManager) != null) {
            val file = filePicker.createTempFile()
            val photoUri = filePicker.uriFromFile(file)

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(takePictureIntent, REQ_CODE_TAKE_PICTURE)

            viewModel.apply {
                userImageObservable.set(photoUri)
            }
        }
    }

    private fun initBanding() {
        binding = DataBindingUtil.setContentView(this, LAYOUT_RES)
        binding.viewModel = viewModel
    }
}