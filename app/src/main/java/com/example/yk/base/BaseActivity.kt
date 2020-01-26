package com.example.yk.base

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.yk.R

abstract class BaseActivity : AppCompatActivity() {

    fun showDialog(message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            .setMessage(message)
            .setTitle(R.string.alert_title)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
        builder.show()
    }
}