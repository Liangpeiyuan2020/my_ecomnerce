package com.zs.my_ecommerce.common

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
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

// 扩展函数：简化单次观察
fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: Observer<T>) {
    observe(owner, object : Observer<T> {
        override fun onChanged(value: T) {
            observer.onChanged(value)
            removeObserver(this) // 触发后立即移除
        }
    })
}