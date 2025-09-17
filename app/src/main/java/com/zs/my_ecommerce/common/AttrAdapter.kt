package com.zs.my_ecommerce.common

import android.widget.ImageView
import android.widget.RatingBar
import androidx.databinding.BindingAdapter

object AttrAdapter {
    /**
     * 加载网络圆角图片
     */
    @BindingAdapter(value = ["android:downloadUrl"])
    @JvmStatic
    fun imgUrlRadiusCircle(view: ImageView, url: String) {
        view.downloadUrl(view.context.applicationContext, url)
    }
//
//    @BindingAdapter("app:ratingDouble")
//    @JvmStatic
//    fun setRating(view: RatingBar, value: Double?) {
//        view.rating = value?.toFloat() ?: 0f
//    }
}