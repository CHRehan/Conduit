package io.realworld.android.conduit.extensions

import android.media.Image
import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.loadImage(uri: String, circleCrop: Boolean = false) {
    Glide
        .with(this)
        .load(uri).apply {
            if (circleCrop)
                circleCrop().into(this@loadImage)
            else
                into(this@loadImage)
        }

}