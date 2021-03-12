package com.sun.imagegif.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadGifUrl(url: String) {
    Glide.with(context)
        .asGif()
        .load(url)
        .into(this)
}

fun ImageView.loadImageUrl(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}
