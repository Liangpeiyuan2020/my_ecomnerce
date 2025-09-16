package com.zs.my_ecommerce.common

import android.widget.ImageView
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
}