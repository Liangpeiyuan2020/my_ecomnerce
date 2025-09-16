package com.zs.my_ecommerce.common

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.zs.my_ecommerce.R

fun ImageView.downloadUrl(context: Context, url: String) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .error(R.drawable.ic_launcher)
        .transition(withCrossFade())
        .transform(GlideRoundTransform(context, 20))
        .into(this)
}