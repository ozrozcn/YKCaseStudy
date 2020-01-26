package com.example.yk.common

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.yk.common.extension.loadImage

object ImageBinding {

    @JvmStatic
    @BindingAdapter("app:imageSource")
    fun loadImage(imageView: ImageView, url: String?) {
        if (url != null && url.isNotEmpty()) {
            imageView.loadImage(url)
        }
    }

    @JvmStatic
    @BindingAdapter("app:imageSource")
    fun loadImage(imageView: ImageView, uri: Uri?) {
        if (uri != null) {
            imageView.loadImage(uri)
        }
    }
}
