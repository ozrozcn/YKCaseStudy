package com.example.yk.common.extension

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImageRound(uri: Uri) {
    Glide.with(this).load(uri).apply(RequestOptions.circleCropTransform()).into(this)
}

fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).into(this)
}

fun ImageView.loadImage(uri: Uri) {
    Glide.with(this).load(uri).into(this)
}