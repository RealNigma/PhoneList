package com.realnigma.phonelist.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun loadImage(url: String?, imageView: ImageView) {
    if (url != null && url.isNotEmpty()) {
        Picasso.get().load(url).into(imageView)
    }
}