package com.ninositsolution.paging.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.io.File

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String) {
    if (imageUrl.startsWith("http")) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    } else {
        Glide.with(imageView.context)
            .load(File(imageUrl))
            .into(imageView)
    }
}