package com.zs.my_ecommerce.common


import android.util.Log
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    protected suspend fun <T> callApi(
        apiCall: suspend () -> T
    ): T? {
        AppGlobals.showGlobalLoading()
        try {
            val result = apiCall()
            AppGlobals.hideGlobalLoading()
            return result
        } catch (e: Exception) {
            AppGlobals.hideGlobalLoading()
            handleApiError(e)
            Log.e("apiRequestError", e.message.toString())
            return null
        }
    }

    // 子类可以覆盖此方法实现特定的错误处理
    protected open fun handleApiError(e: Exception) {
        // 默认实现，子类可以覆盖
    }
}